package com.example.microservicenada.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.microservicenada.Entities.Universite;
import java.util.List;
import java.util.Optional;
import com.example.microservicenada.Repositories.UniversiteRepository;
@Service
@RequiredArgsConstructor
public class UniversiteService {
    private final UniversiteRepository universiteRepository;

    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    public Optional<Universite> getUniversiteById(Long id) {
        return universiteRepository.findById(id);
    }

    public Universite saveUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }
}
