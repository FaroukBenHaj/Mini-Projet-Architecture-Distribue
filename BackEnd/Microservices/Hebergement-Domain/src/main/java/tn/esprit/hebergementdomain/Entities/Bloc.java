package tn.esprit.hebergementdomain.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Bloc {
    @Id
    @GeneratedValue
    private long idBloc;
    private String nomBloc;
    private long capaciteBloc ;
    @OneToMany(mappedBy = "bloc",cascade = CascadeType.ALL)
    private Set<Chambre> myChambre ;

    public Bloc(String nomBloc, long capaciteBloc) {
        this.nomBloc = nomBloc;
        this.capaciteBloc = capaciteBloc;
    }

    public Bloc() {

    }

    public long getIdBloc() {
        return idBloc;
    }

    public void setIdBloc(long idBloc) {
        this.idBloc = idBloc;
    }

    public String getNomBloc() {
        return nomBloc;
    }

    public void setNomBloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }

    public long getCapaciteBloc() {
        return capaciteBloc;
    }

    public void setCapaciteBloc(long capaciteBloc) {
        this.capaciteBloc = capaciteBloc;
    }

    public Set<Chambre> getMyChambre() {
        return myChambre;
    }

    public void setMyChambre(Set<Chambre> myChambre) {
        this.myChambre = myChambre;
    }
}
