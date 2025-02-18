package tn.esprit.hebergementdomain.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.hebergementdomain.Entities.Chambre;

import java.util.List;

public interface IChambreService {
    public Chambre getChambreById(long id);
    public List<Chambre> getAllChambre();
    public Chambre addChambre(Chambre chambre);
    public Chambre updateChambre(Chambre chambre);
    public Chambre deleteChambre(long id);
}
