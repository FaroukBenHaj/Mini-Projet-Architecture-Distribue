import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Payment } from '../models/payment.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'http://localhost:8072/payments';  // Your Spring backend endpoint
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  // Get All Payments
  getAllPayments(): Observable<Payment[]> {
    return this.http.get<Payment[]>(this.apiUrl).pipe(
      tap(data => console.log('Payments received:', data)),
      catchError(this.handleError) // Handling errors more cleanly
    );
  }

  // Create Payment
  createPayment(payment: Payment): Observable<Payment> {
    return this.http.post<Payment>(this.apiUrl, payment, this.httpOptions).pipe(
      tap(data => console.log('Created payment:', data)),
      catchError(this.handleError) // Handling errors more cleanly
    );
  }

  // Update Payment
  updatePayment(id: number, payment: Payment): Observable<Payment> {
    return this.http.put<Payment>(`${this.apiUrl}/${id}`, payment, this.httpOptions).pipe(
      tap(data => console.log('Updated payment:', data)),
      catchError(this.handleError) // Handling errors more cleanly
    );
  }

  // Delete Payment
  deletePayment(id: number): Observable<void> {
  console.log(`Deleting payment with ID: ${id}`); // Log the deletion attempt with the ID
  return this.http.delete<void>(`${this.apiUrl}/payments/${id}`).pipe(
    tap(() => {
      console.log(`Deleted payment with ID: ${id}`); // Log successful deletion
    }),
    catchError(error => {
      // Log the error with specific payment ID
      console.error(`Error deleting payment with ID ${id}:`, error);
      return throwError(() => error); // Re-throw the error for further handling
    })
  );
}


  // Centralized error handling
  private handleError(error: any) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // The backend returned an unsuccessful response code
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
