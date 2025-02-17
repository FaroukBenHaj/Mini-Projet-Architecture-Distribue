package tn.esprit.hebergementdomain.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Bloc {
    @Id
    @GeneratedValue
    private long idBloc;
    private String nomBloc;
    private long capaciteBloc ;
}
