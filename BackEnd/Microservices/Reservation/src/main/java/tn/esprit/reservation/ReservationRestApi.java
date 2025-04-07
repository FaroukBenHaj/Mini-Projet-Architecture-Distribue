package tn.esprit.reservation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")

public class ReservationRestApi {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/list")
    public List<Reservation> list() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservation(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable Long id,
            @RequestBody Reservation reservation) {
        try {
            if (!id.equals(reservation.getIdReservation())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "ID in path and body don't match"));
            }

            Reservation updated = reservationService.updateReservation(reservation);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/stats/annee")
    public ResponseEntity<Map<Integer, Long>> getReservationStatsByYear() {
        Map<Integer, Long> stats = reservationService.getReservationStatsByYear();
        return ResponseEntity.ok(stats);
    }

    @GetMapping(value = "/export-csv", produces = "text/csv")
    public ResponseEntity<InputStreamResource> exportReservationsCSV() {
        try {
            ByteArrayInputStream csvStream = reservationService.exportValidReservationsToCSV();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reservations.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new InputStreamResource(csvStream));
        } catch (Exception e) {
            String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new InputStreamResource(new ByteArrayInputStream(errorJson.getBytes())));
        }
    }
}

