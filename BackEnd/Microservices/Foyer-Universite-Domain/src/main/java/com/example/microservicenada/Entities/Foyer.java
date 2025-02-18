package com.example.microservicenada.Entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int capacite;

    @OneToOne
    @JoinColumn(name = "universite_id", referencedColumnName = "id")
    private Universite universite;
}