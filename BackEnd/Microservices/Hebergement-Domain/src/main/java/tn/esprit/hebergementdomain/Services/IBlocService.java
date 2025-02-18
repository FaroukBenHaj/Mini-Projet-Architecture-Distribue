package tn.esprit.hebergementdomain.Services;

import tn.esprit.hebergementdomain.Entities.Bloc;

import java.util.List;

public interface IBlocService{
    public Bloc addBloc(Bloc bloc);
    public Bloc updateBloc(Bloc bloc);
    public void deleteBloc(Long idBloc);
    public Bloc getBlocById(Long idBloc);
    public List<Bloc> getAllBloc();
}
