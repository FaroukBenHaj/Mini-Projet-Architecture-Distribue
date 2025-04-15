import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Reservation {
  idReservation?: number;
  anneeUniversitaire: Date | string; // Allow both Date and string types
  estValide: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = 'http://localhost:8088/reservations';
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getAllReservations(): Observable<Reservation[]> {
    console.log('Fetching all reservations from:', `${this.apiUrl}/list`);
    return this.http.get<Reservation[]>(`${this.apiUrl}/list`).pipe(
      tap(data => console.log('Reservations received:', data)),
      catchError(error => {
        console.error('Error fetching reservations:', error);
        return throwError(() => error);
      })
    );
  }

  getReservationById(id: number): Observable<Reservation> {
    console.log(`Fetching reservation with ID ${id}`);
    return this.http.get<Reservation>(`${this.apiUrl}/${id}`).pipe(
      tap(data => console.log('Reservation retrieved:', data)),
      catchError(error => {
        console.error(`Error fetching reservation with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  createReservation(reservation: Reservation): Observable<Reservation> {
    // Format the reservation before sending
    const formattedReservation = this.formatReservationForServer(reservation);
    console.log('Creating reservation with data:', formattedReservation);
    
    return this.http.post<Reservation>(`${this.apiUrl}/create`, formattedReservation, this.httpOptions).pipe(
      tap(data => console.log('Created reservation:', data)),
      catchError(error => {
        console.error('Error creating reservation:', error);
        return throwError(() => error);
      })
    );
  }

  updateReservation(id: number, reservation: Reservation): Observable<Reservation> {
    const formattedReservation = this.formatReservationForServer(reservation);
    console.log(`Updating reservation ${id} with data:`, formattedReservation);
    
    return this.http.put<Reservation>(`${this.apiUrl}/${id}`, formattedReservation, this.httpOptions).pipe(
      tap(data => console.log('Updated reservation:', data)),
      catchError(error => {
        console.error(`Error updating reservation with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  deleteReservation(id: number): Observable<void> {
    console.log(`Deleting reservation with ID: ${id}`);
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => console.log(`Deleted reservation with ID: ${id}`)),
      catchError(error => {
        console.error(`Error deleting reservation with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  // Helper to format data properly for the server
  private formatReservationForServer(reservation: Reservation): any {
    const formatted = { ...reservation };
    
    // Ensure anneeUniversitaire is properly formatted (ISO format)
    if (formatted.anneeUniversitaire) {
      const dateValue = formatted.anneeUniversitaire;
      
      if (typeof dateValue === 'string') {
        // Check if date string already has time component
        if (!dateValue.includes('T')) {
          // Add time component if missing
          formatted.anneeUniversitaire = dateValue + 'T00:00:00.000Z';
        }
      } else if (dateValue instanceof Date) {
        // Convert Date object to ISO string
        formatted.anneeUniversitaire = dateValue.toISOString();
      }
    }
    
    return formatted;
  }
}