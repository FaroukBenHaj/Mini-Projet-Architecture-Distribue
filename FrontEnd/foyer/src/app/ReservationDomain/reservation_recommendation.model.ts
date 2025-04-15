// reservation_recommendation.model.ts

export interface ReservationRecommendation {
    recommendedDate: string;
    successProbability: number;
    predictedDemandLevel: string; // 'LOW', 'MEDIUM', 'HIGH'
    reasonForRecommendation: string;
    alternativeDates: string[];
  }
  
  export interface ReservationForecast {
    year: number;
    predicted_total: number;
    confidence_level: string; // 'LOW', 'MEDIUM', 'HIGH'
    monthly_distribution: {
      [month: string]: number;
    };
  }