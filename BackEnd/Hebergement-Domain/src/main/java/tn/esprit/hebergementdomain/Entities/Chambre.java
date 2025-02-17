package tn.esprit.hebergementdomain.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Chambre {
    @Id
    @GeneratedValue
    private long idChambre;
    private long numeroChambre;
    private TypeChambre typeC;
}
