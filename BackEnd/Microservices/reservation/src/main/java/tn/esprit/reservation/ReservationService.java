package tn.esprit.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation updateReservation(Reservation reservation) {
        if (reservationRepository.existsById(reservation.getIdReservation())) {
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Map<Integer, Long> getReservationStatsByYear() {
        List<Object[]> results = reservationRepository.countReservationsByYear();
        Map<Integer, Long> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            Integer year = (Integer) result[0];
            Long count = (Long) result[1];
            stats.put(year, count);
        }

        return stats;
    }
    public ByteArrayInputStream exportValidReservationsToCSV() throws Exception {
        List<Reservation> reservations = reservationRepository.findByEstValideTrue();

        if (reservations.isEmpty()) {
            throw new Exception("No validated reservations found");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(out)) {
            writer.println("ID,Date Reservation,Valid");

            for (Reservation reservation : reservations) {
                writer.println(String.join(",",
                        reservation.getIdReservation().toString(),
                        reservation.getAnneeUniversitaire().toString(),
                        reservation.getEstValide().toString()
                ));
            }
            writer.flush();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}