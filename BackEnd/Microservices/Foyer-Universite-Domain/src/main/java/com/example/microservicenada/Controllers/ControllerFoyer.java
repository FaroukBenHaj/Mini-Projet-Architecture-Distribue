package com.example.microservicenada.Controllers;

import lombok.RequiredArgsConstructor;
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
}
