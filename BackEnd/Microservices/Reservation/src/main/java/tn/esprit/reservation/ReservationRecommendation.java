package tn.esprit.reservation;

import java.util.Date;
import java.util.List;

public class ReservationRecommendation {
    private Date recommendedDate;
    private Double successProbability;
    private String reasonForRecommendation;
    private List<Date> alternativeDates;
    private String predictedDemandLevel;

    public ReservationRecommendation() {
    }

    public ReservationRecommendation(Date recommendedDate, Double successProbability,
                                     String reasonForRecommendation, List<Date> alternativeDates,
                                     String predictedDemandLevel) {
        this.recommendedDate = recommendedDate;
        this.successProbability = successProbability;
        this.reasonForRecommendation = reasonForRecommendation;
        this.alternativeDates = alternativeDates;
        this.predictedDemandLevel = predictedDemandLevel;
    }

    public Date getRecommendedDate() {
        return recommendedDate;
    }

    public void setRecommendedDate(Date recommendedDate) {
        this.recommendedDate = recommendedDate;
    }

    public Double getSuccessProbability() {
        return successProbability;
    }

    public void setSuccessProbability(Double successProbability) {
        this.successProbability = successProbability;
    }

    public String getReasonForRecommendation() {
        return reasonForRecommendation;
    }

    public void setReasonForRecommendation(String reasonForRecommendation) {
        this.reasonForRecommendation = reasonForRecommendation;
    }

    public List<Date> getAlternativeDates() {
        return alternativeDates;
    }

    public void setAlternativeDates(List<Date> alternativeDates) {
        this.alternativeDates = alternativeDates;
    }

    public String getPredictedDemandLevel() {
        return predictedDemandLevel;
    }

    public void setPredictedDemandLevel(String predictedDemandLevel) {
        this.predictedDemandLevel = predictedDemandLevel;
    }
}