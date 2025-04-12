package tn.esprit.reservation;

import tn.esprit.reservation.ReservationRecommendation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Slf4j

@Service
public class ReservationAIService {

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationRecommendation getReservationRecommendation(Date targetDate) {
        Map<Integer, Long> historicalData = getHistoricalReservationPatterns();

        Map<Integer, Integer> monthDistribution = getCurrentYearMonthDistribution();

        String demandLevel = predictDemandForDate(targetDate, historicalData, monthDistribution);

        Date recommendedDate = calculateOptimalReservationDate(targetDate, demandLevel);

        double successProbability = calculateSuccessProbability(demandLevel, daysBetween(new Date(), targetDate));

        String reason = generateRecommendationReason(demandLevel, targetDate, recommendedDate);

        List<Date> alternatives = generateAlternativeDates(targetDate, demandLevel);

        return new ReservationRecommendation(
                recommendedDate,
                successProbability,
                reason,
                alternatives,
                demandLevel
        );
    }

    private Map<Integer, Long> getHistoricalReservationPatterns() {
        List<Object[]> results = reservationRepository.getYearlyReservationCounts();
        Map<Integer, Long> historicalData = new HashMap<>();

        for (Object[] result : results) {
            Integer year = (Integer) result[0];
            Long count = (Long) result[1];
            historicalData.put(year, count);
        }

        return historicalData;
    }
    private Map<Integer, Integer> getCurrentYearMonthDistribution() {
        Map<Integer, Integer> distribution = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        distribution.put(1, 5);   // January
        distribution.put(2, 8);   // February
        distribution.put(3, 12);  // March
        distribution.put(4, 10);  // April
        distribution.put(5, 15);  // May
        distribution.put(6, 25);  // June
        distribution.put(7, 40);  // July
        distribution.put(8, 60);  // August
        distribution.put(9, 45);  // September
        distribution.put(10, 20); // October
        distribution.put(11, 10); // November
        distribution.put(12, 5);  // December

        return distribution;
    }

    private String predictDemandForDate(Date date, Map<Integer, Long> historicalData, Map<Integer, Integer> monthDistribution) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;

        double yearlyGrowthFactor = calculateYearlyGrowthFactor(historicalData);

        Integer monthlyFactor = monthDistribution.getOrDefault(month, 10);

        double demandScore = monthlyFactor * yearlyGrowthFactor;

        if (demandScore > 50) {
            return "HIGH";
        } else if (demandScore > 20) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    private double calculateYearlyGrowthFactor(Map<Integer, Long> historicalData) {
        if (historicalData.size() < 2) {
            return 1.05;
        }

        SimpleRegression regression = new SimpleRegression();
        for (Map.Entry<Integer, Long> entry : historicalData.entrySet()) {
            regression.addData(entry.getKey(), entry.getValue());
        }

        double slope = regression.getSlope();
        return 1.0 + (slope / 100);
    }

    private Date calculateOptimalReservationDate(Date targetDate, String demandLevel) {
        Calendar cal = Calendar.getInstance();
        Calendar targetCal = Calendar.getInstance();
        targetCal.setTime(targetDate);

        switch (demandLevel) {
            case "HIGH":
                cal.setTime(targetDate);
                cal.add(Calendar.MONTH, -3);
                if (cal.before(Calendar.getInstance())) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case "MEDIUM":
                cal.setTime(targetDate);
                cal.add(Calendar.MONTH, -1);
                if (cal.before(Calendar.getInstance())) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case "LOW":
                cal.setTime(targetDate);
                cal.add(Calendar.DAY_OF_MONTH, -14);
                if (cal.before(Calendar.getInstance())) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            default:
                cal.setTime(targetDate);
                cal.add(Calendar.MONTH, -1);
        }

        return cal.getTime();
    }

    private double calculateSuccessProbability(String demandLevel, long daysUntilTarget) {
        double baseProbability;
        switch (demandLevel) {
            case "HIGH":
                baseProbability = 0.5;
                break;
            case "MEDIUM":
                baseProbability = 0.7;
                break;
            case "LOW":
                baseProbability = 0.9;
                break;
            default:
                baseProbability = 0.6;
        }

        double timeAdjustment;
        if (daysUntilTarget > 90) {
            timeAdjustment = 0.4;
        } else if (daysUntilTarget > 30) {
            timeAdjustment = 0.3;
        } else if (daysUntilTarget > 14) {
            timeAdjustment = 0.2;
        } else if (daysUntilTarget > 7) {
            timeAdjustment = 0.1;
        } else if (daysUntilTarget > 1) {
            timeAdjustment = 0.0;
        } else {
            timeAdjustment = -0.3;
        }

        double finalProbability = Math.min(0.99, baseProbability + timeAdjustment);
        return Math.round(finalProbability * 100.0) / 100.0;
    }

    private String generateRecommendationReason(String demandLevel, Date targetDate, Date recommendedDate) {
        long daysDifference = daysBetween(recommendedDate, targetDate);

        StringBuilder reason = new StringBuilder();
        reason.append("Based on historical data, we've detected ");
        reason.append(demandLevel.toLowerCase());
        reason.append(" demand for your desired reservation date. ");

        if (daysDifference > 60) {
            reason.append("We recommend booking about ").append(daysDifference / 30).append(" months in advance ");
        } else if (daysDifference > 7) {
            reason.append("We recommend booking about ").append(daysDifference / 7).append(" weeks in advance ");
        } else {
            reason.append("We recommend booking as soon as possible ");
        }

        reason.append("to maximize your chances of securing your preferred accommodation.");

        return reason.toString();
    }

    private List<Date> generateAlternativeDates(Date targetDate, String demandLevel) {
        List<Date> alternatives = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);

        for (int i = 1; i <= 3; i++) {
            if ("HIGH".equals(demandLevel)) {
                cal.add(Calendar.DAY_OF_MONTH, -7 * i);
            } else {
                cal.add(Calendar.DAY_OF_MONTH, 7 * i);
            }
            alternatives.add(cal.getTime());
            cal.setTime(targetDate);
        }

        return alternatives;
    }

    private long daysBetween(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Math.abs(ChronoUnit.DAYS.between(localDate1, localDate2));
    }

    public Map<String, Object> predictNextYearReservations() {
        try {
            Map<Integer, Long> historicalData = getHistoricalReservationPatterns();

            if (historicalData.isEmpty()) {
                return getDefaultForecast();
            }

            SimpleRegression regression = new SimpleRegression();
            historicalData.forEach((year, count) ->
                    regression.addData(year, count));

            int nextYear = Year.now().getValue() + 1;
            double predictedValue = Math.max(0, regression.predict(nextYear));

            return Map.of(
                    "year", nextYear,
                    "predicted_total", (int) predictedValue,
                    "monthly_distribution", getMonthlyDistribution(predictedValue),
                    "confidence_level", calculateConfidenceLevel(historicalData.size()),
                    "historical_data_points", historicalData.size()
            );
        } catch (Exception e) {
            log.error("Forecast failed", e);
            return getDefaultForecast();
        }
    }

    private Map<String, Integer> getMonthlyDistribution(double predictedValue) {
        Map<Integer, Integer> distribution = getCurrentYearMonthDistribution();
        Map<String, Integer> monthlyDistribution = new LinkedHashMap<>();

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int i = 0; i < months.length; i++) {
            monthlyDistribution.put(months[i],
                    (int) (predictedValue * distribution.get(i+1) / 100));
        }

        return monthlyDistribution;
    }

    private Map<String, Object> getDefaultForecast() {
        return Map.of(
                "year", Year.now().getValue() + 1,
                "predicted_total", 100,
                "monthly_distribution", getMonthlyDistribution(100),
                "confidence_level", "LOW",
                "note", "Default forecast used due to insufficient data"
        );
    }
    private String calculateConfidenceLevel(int dataPointsCount) {
        if (dataPointsCount > 5) return "HIGH";
        if (dataPointsCount > 2) return "MEDIUM";
        return "LOW";
    }
}