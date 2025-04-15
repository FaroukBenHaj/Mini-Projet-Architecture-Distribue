import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { ReservationForecast, ReservationRecommendation } from './reservation_recommendation.model';
@Injectable({
  providedIn: 'root'
})
export class ReservationAiService {
  private baseUrl = 'http://localhost:8082/reservations/ai';

  constructor(private http: HttpClient) { }

  getReservationRecommendation(targetDate: string): Observable<ReservationRecommendation> {
    return this.http.get<ReservationRecommendation>(`${this.baseUrl}/recommendation?targetDate=${targetDate}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getNextYearForecast(): Observable<ReservationForecast> {
    return this.http.get<ReservationForecast>(`${this.baseUrl}/forecast`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
