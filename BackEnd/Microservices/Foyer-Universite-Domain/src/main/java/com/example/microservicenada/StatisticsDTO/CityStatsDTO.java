package com.example.microservicenada.StatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;



@AllArgsConstructor
@Data
public class CityStatsDTO {
    private String city;
    private long foyerCount;
    private int totalCapacity;
    // Constructor for countFoyersByCity()
    public CityStatsDTO(String city, long foyerCount) {
        this.city = city;
        this.foyerCount = foyerCount;
    }

    // Constructor for sumCapacityByCity()
    public CityStatsDTO(String city, int totalCapacity) {
        this.city = city;
        this.totalCapacity = totalCapacity;
    }
    public long getFoyerCount() {
        return foyerCount;
    }

    public void setFoyerCount(long foyerCount) {
        this.foyerCount = foyerCount;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}