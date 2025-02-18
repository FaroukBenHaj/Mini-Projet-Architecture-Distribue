package tn.esprit.hebergementdomain.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.hebergementdomain.Entities.Chambre;
import tn.esprit.hebergementdomain.Reposiitories.ChambreRepository;

import java.util.List;
@Service
public class ChambreService implements IChambreService {
    @Autowired
    private ChambreRepository chambreRepository;
    @Override
    public Chambre getChambreById(long id) {
        return null;
    }

    @Override
    public List<Chambre> getAllChambre() {
        return List.of();
    }

    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public Chambre updateChambre(Chambre chambre) {
        return null;
    }

    @Override
    public Chambre deleteChambre(long id) {
        return null;
    }
}
