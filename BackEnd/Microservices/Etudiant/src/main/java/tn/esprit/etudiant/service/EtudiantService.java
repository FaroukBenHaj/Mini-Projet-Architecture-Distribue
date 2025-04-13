
package tn.esprit.etudiant.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tn.esprit.etudiant.entity.Etudiant;
import tn.esprit.etudiant.entity.Reservation;
import tn.esprit.etudiant.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // Récupérer un étudiant par son ID
    public Optional<Etudiant> getEtudiantById(Long idEtudiant) {
        return etudiantRepository.findById(idEtudiant);
    }

    // Ajouter un nouvel étudiant
    @Transactional
    public Etudiant saveEtudiant(Etudiant etudiant) {
        // Créer une copie de la collection pour éviter les problèmes de modification concurrente
        Set<Reservation> originalReservations = new HashSet<>();
        if (etudiant.getReservations() != null) {
            originalReservations.addAll(etudiant.getReservations());
        }

        // Vider temporairement les réservations
        etudiant.setReservations(new HashSet<>());

        // Sauvegarder l'étudiant sans réservations
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        // Ajouter les réservations existantes
        for (Reservation reservation : originalReservations) {
            if (reservation.getIdReservation() != null) {
                Reservation existingReservation = entityManager.find(Reservation.class, reservation.getIdReservation());
                if (existingReservation != null) {
                    savedEtudiant.getReservations().add(existingReservation);
                }
            }
        }

        // Sauvegarder l'étudiant avec les réservations
        return etudiantRepository.save(savedEtudiant);
    }

    // Mettre à jour un étudiant existant
    @Transactional
    public Etudiant updateEtudiant(Long idEtudiant, Etudiant etudiantDetails) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID : " + idEtudiant));

        etudiant.setNomEt(etudiantDetails.getNomEt());
        etudiant.setPrenomEt(etudiantDetails.getPrenomEt());
        etudiant.setCin(etudiantDetails.getCin());
        etudiant.setEcole(etudiantDetails.getEcole());
        etudiant.setDateNaissance(etudiantDetails.getDateNaissance());

        // Gérer les réservations de la même manière que dans saveEtudiant
        if (etudiantDetails.getReservations() != null && !etudiantDetails.getReservations().isEmpty()) {
            etudiant.getReservations().clear();

            for (Reservation reservation : etudiantDetails.getReservations()) {
                if (reservation.getIdReservation() != null) {
                    Reservation existingReservation = entityManager.find(Reservation.class, reservation.getIdReservation());
                    if (existingReservation != null) {
                        etudiant.getReservations().add(existingReservation);
                    }
                }
            }
        }

        return etudiantRepository.save(etudiant);
    }

    // Supprimer un étudiant
    public void deleteEtudiant(Long idEtudiant) {
        etudiantRepository.deleteById(idEtudiant);
    }
}