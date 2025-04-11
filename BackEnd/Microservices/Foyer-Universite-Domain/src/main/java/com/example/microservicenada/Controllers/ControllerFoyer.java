package com.example.microservicenada.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.microservicenada.Entities.Foyer;
import com.example.microservicenada.Services.FoyerService;

@RestController
@RequestMapping("/foyers")
@RequiredArgsConstructor
class FoyerController {
    @Autowired
    private final FoyerService foyerService;

    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foyer> getFoyerById(@PathVariable Long id) {
        Optional<Foyer> foyer = foyerService.getFoyerById(id);
        return foyer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Foyer createFoyer(@RequestBody Foyer foyer) {
        return foyerService.saveFoyer(foyer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
        return ResponseEntity.noContent().build();
    }
    // PUT endpoint to update an existing Foyer
    @PutMapping("/{id}")
    public ResponseEntity<Foyer> updateFoyer(@PathVariable Long id, @RequestBody Foyer foyerDetails) {
        Foyer updatedFoyer = foyerService.updateFoyer(id, foyerDetails);
        return ResponseEntity.ok(updatedFoyer);  // Return the updated Foyer
    }



        @PostMapping("/{foyerId}/assign-to-universite/{universiteId}")
        public ResponseEntity<Foyer> assignToUniversite(
                @PathVariable Long foyerId,
                @PathVariable Long universiteId) {
            Foyer foyer = foyerService.assignFoyerToUniversite(foyerId, universiteId);
            return ResponseEntity.ok(foyer);
        }

}
