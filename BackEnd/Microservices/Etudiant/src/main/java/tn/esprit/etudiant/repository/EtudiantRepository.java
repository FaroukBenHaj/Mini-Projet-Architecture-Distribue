package tn.esprit.etudiant.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.etudiant.entity.Etudiant;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // Vous pouvez ajouter ici des méthodes customisées si nécessaire
}
