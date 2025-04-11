package com.example.microservicenada.Services;

import com.example.microservicenada.Entities.Universite;
import com.example.microservicenada.Repositories.UniversiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservicenada.Entities.Foyer;
import com.example.microservicenada.Repositories.FoyerRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoyerService {
    @Autowired
    private final FoyerRepository foyerRepository;
    @Autowired
    private UniversiteRepository universiteRepository;


    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    public Optional<Foyer> getFoyerById(Long id) {
        return foyerRepository.findById(id);
    }

    public Foyer saveFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    public void deleteFoyer(Long id) {
        foyerRepository.deleteById(id);
    }

    // New method for updating a Foyer
    public Foyer updateFoyer(Long id, Foyer foyerDetails) {
        // Check if the Foyer exists
        Foyer foyer = foyerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foyer not found with id: " + id));

        // Update the foyer fields
        foyer.setNom(foyerDetails.getNom());
        foyer.setCapacite(foyerDetails.getCapacite());
        foyer.setUniversite(foyerDetails.getUniversite()); // This will update the universite as well if needed

        // Save the updated foyer
        return foyerRepository.save(foyer);
    }

    // Assign a Foyer to a Universite
    public Foyer assignFoyerToUniversite(Long foyerId, Long universiteId) {
        Foyer foyer = foyerRepository.findById(foyerId)
                .orElseThrow(() -> new RuntimeException("Foyer not found"));
        Universite universite = universiteRepository.findById(universiteId)
                .orElseThrow(() -> new RuntimeException("Universite not found"));

        // Set the relationship on both sides
        foyer.setUniversite(universite);
        universite.setFoyer(foyer);

        // Save both entities
        universiteRepository.save(universite);
        return foyerRepository.save(foyer);
    }


}
