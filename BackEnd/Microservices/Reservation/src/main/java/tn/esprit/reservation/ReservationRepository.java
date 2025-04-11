package tn.esprit.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT YEAR(r.anneeUniversitaire) as annee, COUNT(r) as nombre " +
            "FROM Reservation r " +
            "GROUP BY YEAR(r.anneeUniversitaire) " +
            "ORDER BY YEAR(r.anneeUniversitaire) DESC")
    List<Object[]> countReservationsByYear();

    List<Reservation> findByEstValideTrue();

}
