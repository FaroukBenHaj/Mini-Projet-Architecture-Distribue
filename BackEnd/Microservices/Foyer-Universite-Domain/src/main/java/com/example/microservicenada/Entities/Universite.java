package com.example.microservicenada.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String email;

    @OneToOne(mappedBy = "universite", cascade = CascadeType.ALL)
    private Foyer foyer;
}

