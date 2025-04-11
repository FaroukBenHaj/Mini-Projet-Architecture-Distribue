package com.example.microservicenada.StatisticsDTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummaryStatsDTO {
    private double averageCapacity;
    private int minCapacity;
    private int maxCapacity;
    private long totalFoyers;

    // Optionally, you can keep this all-args constructor too
    public SummaryStatsDTO(double averageCapacity, int minCapacity, int maxCapacity, long totalFoyers) {
        this.averageCapacity = averageCapacity;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
        this.totalFoyers = totalFoyers;
    }
}