package tn.esprit.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue

    private String idReservation ;
    private Date AnneeUniversitaire;
    private Boolean estValide;

     public Reservation() {}
    public Reservation(String idReservation, Date AnneeUniversitaire, Boolean estValide) {}

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public Date getAnneeUniversitaire() {
        return AnneeUniversitaire;
    }

    public void setAnneeUniversitaire(Date anneeUniversitaire) {
        AnneeUniversitaire = anneeUniversitaire;
    }

    public Boolean getEstValide() {
        return estValide;
    }

    public void setEstValide(Boolean estValide) {
        this.estValide = estValide;
    }
}
