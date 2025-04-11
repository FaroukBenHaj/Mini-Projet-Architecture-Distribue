package com.example.microservicenada.Controllers;


import com.example.microservicenada.StatisticsDTO.CapacityRangeDTO;
import com.example.microservicenada.StatisticsDTO.CityStatsDTO;
import com.example.microservicenada.StatisticsDTO.SummaryStatsDTO;
import com.example.microservicenada.Services.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/foyer-count-per-city")
    public List<CityStatsDTO> getFoyerCountPerCity() {
        return statisticsService.getFoyerCountPerCity();
    }

    @GetMapping("/total-capacity-per-city")
    public List<CityStatsDTO> getTotalCapacityPerCity() {
        return statisticsService.getTotalCapacityPerCity();
    }

    @GetMapping("/capacity-range-distribution")
    public List<CapacityRangeDTO> getCapacityRangeDistribution() {
        return statisticsService.getCapacityRangeDistribution();
    }

    @GetMapping("/summary-statistics")
    public SummaryStatsDTO getSummaryStatistics() {
        return statisticsService.getSummaryStatistics();
    }
}