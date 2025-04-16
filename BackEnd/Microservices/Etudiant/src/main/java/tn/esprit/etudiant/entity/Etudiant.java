
package tn.esprit.etudiant.entity;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etudiant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant;

    @Column(name = "nom_et", nullable = false)
    @NotNull(message = "Le nom de l'étudiant ne peut pas être nul")
    private String nomEt;

    @Column(name = "prenom_et", nullable = false)
    @NotNull(message = "Le prénom de l'étudiant ne peut pas être nul")
    private String prenomEt;

    @Column(name = "cin", nullable = false, unique = true)
    @NotNull(message = "Le CIN de l'étudiant ne peut pas être nul")
    private Long cin;

    @Column(name = "ecole", nullable = false)
    @NotNull(message = "L'école de l'étudiant ne peut pas être nulle")
    private String ecole;

    @Column(name = "date_naissance", nullable = false)
    @NotNull(message = "La date de naissance de l'étudiant ne peut pas être nulle")
    private Date dateNaissance;

    // Changed cascade type - removed PERSIST
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "etudiant_reservation",
            joinColumns = @JoinColumn(name = "id_etudiant"),
            inverseJoinColumns = @JoinColumn(name = "id_reservation")
    )
    private Set<Reservation> reservations = new HashSet<>();
}

