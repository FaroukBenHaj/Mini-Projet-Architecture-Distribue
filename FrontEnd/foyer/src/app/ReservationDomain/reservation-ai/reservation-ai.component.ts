import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ReservationAiService } from '../reservationAi.service';
import { ReservationForecast, ReservationRecommendation } from '../reservation_recommendation.model';

@Component({
  selector: 'app-reservation-ai',
  templateUrl: './reservation-ai.component.html',
  styleUrls: ['./reservation-ai.component.css'],
  providers: [DatePipe]
})
export class ReservationAiComponent implements OnInit {
  recommendationForm: FormGroup;
  recommendation: ReservationRecommendation | null = null;
  forecast: ReservationForecast | null = null;
  loading = false;
  error = '';
  showForecast = false;
  minDate: string; // Added property to replace [today]
  
  // Chart data
  chartData: any[] = [];
  
  constructor(
    private fb: FormBuilder,
    private reservationAiService: ReservationAiService,
    private datePipe: DatePipe
  ) {
    this.recommendationForm = this.fb.group({
      targetDate: ['', [Validators.required]]
    });
    
    // Set minDate to today's date in the format yyyy-MM-dd
    this.minDate = this.formatDateAsIsoString(new Date());
  }

  ngOnInit(): void {
    this.getNextYearForecast();
  }

  // Helper method to format date as ISO string for min attribute
  formatDateAsIsoString(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  getRecommendation(): void {
    if (this.recommendationForm.invalid) {
      return;
    }
    
    this.loading = true;
    this.error = '';
    
    const targetDate = this.datePipe.transform(
      this.recommendationForm.get('targetDate')?.value,
      'yyyy-MM-dd'
    );
    
    if (!targetDate) {
      this.error = 'Invalid date format';
      this.loading = false;
      return;
    }
    
    this.reservationAiService.getReservationRecommendation(targetDate)
      .subscribe({
        next: (data) => {
          this.recommendation = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to get recommendation. Please try again.';
          console.error('Error fetching recommendation:', err);
          this.loading = false;
        }
      });
  }
  
  getNextYearForecast(): void {
    this.reservationAiService.getNextYearForecast()
      .subscribe({
        next: (data) => {
          this.forecast = data;
          this.prepareChartData(data);
        },
        error: (err) => {
          console.error('Error fetching forecast:', err);
        }
      });
  }
  
  prepareChartData(forecast: ReservationForecast): void {
    this.chartData = [];
    const months = Object.keys(forecast.monthly_distribution);
    
    for (const month of months) {
      this.chartData.push({
        name: month,
        value: forecast.monthly_distribution[month]
      });
    }
  }
  
  toggleForecast(): void {
    this.showForecast = !this.showForecast;
  }
  
  getConfidenceBadgeClass(): string {
    if (!this.forecast) return 'bg-secondary';
    
    switch (this.forecast.confidence_level) {
      case 'HIGH': return 'bg-success';
      case 'MEDIUM': return 'bg-warning';
      case 'LOW': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }
  
  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    return this.datePipe.transform(new Date(dateStr), 'MMM d, yyyy') || '';
  }
  
  getProbabilityColorClass(probability: number): string {
    if (probability >= 0.8) return 'text-success';
    if (probability >= 0.5) return 'text-warning';
    return 'text-danger';
  }
  
  getDemandBadgeClass(demand: string): string {
    switch (demand) {
      case 'HIGH': return 'bg-danger';
      case 'MEDIUM': return 'bg-warning';
      case 'LOW': return 'bg-success';
      default: return 'bg-secondary';
    }
  }
}