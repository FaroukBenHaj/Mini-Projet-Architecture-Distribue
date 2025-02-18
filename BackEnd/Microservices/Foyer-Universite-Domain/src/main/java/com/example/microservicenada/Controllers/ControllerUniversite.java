package com.example.microservicenada.Controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.microservicenada.Entities.Universite;
import com.example.microservicenada.Services.UniversiteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/universites")
@RequiredArgsConstructor
public class ControllerUniversite {



    private final UniversiteService universiteService;

    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Universite> getUniversiteById(@PathVariable Long id) {
        Optional<Universite> universite = universiteService.getUniversiteById(id);
        return universite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Universite createUniversite(@RequestBody Universite universite) {
        return universiteService.saveUniversite(universite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
        return ResponseEntity.noContent().build();
    }
}