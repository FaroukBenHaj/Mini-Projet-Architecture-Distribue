package tn.esprit.hebergementdomain.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.hebergementdomain.Entities.Bloc;

import java.util.List;

@Service
public class BlocService implements IBlocService {
    @Override
    public Bloc addBloc(Bloc bloc) {
        return null;
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return null;
    }

    @Override
    public void deleteBloc(Long idBloc) {

    }

    @Override
    public Bloc getBlocById(Long idBloc) {
        return null;
    }

    @Override
    public List<Bloc> getAllBloc() {
        return List.of();
    }
    /**@Autowired
    private BlocRepository blocRepository;

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return null;
    }

    @Override
    public void deleteBloc(Long idBloc) {

    }

    @Override
    public Bloc getBlocById(Long idBloc) {
        return null;
    }

    @Override
    public List<Bloc> getAllBloc() {
        return blocRepository.findAll();
    }*/
}
