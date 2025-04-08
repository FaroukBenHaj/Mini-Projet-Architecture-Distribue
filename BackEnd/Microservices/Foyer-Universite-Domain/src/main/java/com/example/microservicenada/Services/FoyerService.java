package com.example.microservicenada.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.microservicenada.Entities.Foyer;
import com.example.microservicenada.Repositories.FoyerRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoyerService {
    private final FoyerRepository foyerRepository;

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




}
