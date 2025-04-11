package com.example.microservicenada.Repositories;

import com.example.microservicenada.StatisticsDTO.CityStatsDTO;

import com.example.microservicenada.Entities.Foyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {
    @Query("SELECT new com.example.microservicenada.StatisticsDTO.CityStatsDTO(u.ville as city, COUNT(f) as foyerCount) " +
            "FROM Foyer f JOIN f.universite u GROUP BY u.ville")
    List<CityStatsDTO> countFoyersByCity();

    @Query("SELECT new com.example.microservicenada.StatisticsDTO.CityStatsDTO(u.ville as city, SUM(f.capacite) as totalCapacity) " +
            "FROM Foyer f JOIN f.universite u GROUP BY u.ville")
    List<CityStatsDTO> sumCapacityByCity();

    @Query("SELECT AVG(f.capacite) FROM Foyer f")
    double getAverageCapacity();

    @Query("SELECT MIN(f.capacite) FROM Foyer f")
    int getMinCapacity();

    @Query("SELECT MAX(f.capacite) FROM Foyer f")
    int getMaxCapacity();

    long countByCapaciteBetween(int min, int max);

    long countByCapaciteGreaterThan(int capacity);

}