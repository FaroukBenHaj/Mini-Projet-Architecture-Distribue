package com.example.microservicenada.Services;

import  com.example.microservicenada.StatisticsDTO.*;
import  com.example.microservicenada.Repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final FoyerRepository foyerRepository;

    public StatisticsService(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }

    public List<CityStatsDTO> getFoyerCountPerCity() {
        return foyerRepository.countFoyersByCity();
    }

    public List<CityStatsDTO> getTotalCapacityPerCity() {
        return foyerRepository.sumCapacityByCity();
    }

    public List<CapacityRangeDTO> getCapacityRangeDistribution() {
        List<CapacityRangeDTO> ranges = new ArrayList<>();

        ranges.add(new CapacityRangeDTO("0-50", foyerRepository.countByCapaciteBetween(0, 50)));
        ranges.add(new CapacityRangeDTO("51-100", foyerRepository.countByCapaciteBetween(51, 100)));
        ranges.add(new CapacityRangeDTO("101-150", foyerRepository.countByCapaciteBetween(101, 150)));
        ranges.add(new CapacityRangeDTO("151-200", foyerRepository.countByCapaciteBetween(151, 200)));
        ranges.add(new CapacityRangeDTO("201+", foyerRepository.countByCapaciteGreaterThan(200)));

        return ranges;
    }

    public SummaryStatsDTO getSummaryStatistics() {
        SummaryStatsDTO stats = new SummaryStatsDTO();

        stats.setAverageCapacity(foyerRepository.getAverageCapacity());
        stats.setMinCapacity(foyerRepository.getMinCapacity());
        stats.setMaxCapacity(foyerRepository.getMaxCapacity());
        stats.setTotalFoyers(foyerRepository.count());

        return stats;
    }
}