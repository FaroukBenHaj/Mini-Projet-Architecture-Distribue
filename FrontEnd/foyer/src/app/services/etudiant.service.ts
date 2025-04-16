import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Etudiant } from '../models/etudiant.model';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {
  private apiUrl = 'http://localhost:8088/api/etudiants';
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getAllEtudiants(): Observable<Etudiant[]> {
    console.log('Fetching all students from:', this.apiUrl);
    return this.http.get<Etudiant[]>(this.apiUrl).pipe(
      tap(data => console.log('Students received:', data)),
      catchError(error => {
        console.error('Error fetching students:', error);
        return throwError(() => error);
      })
    );
  }

  getEtudiantById(id: number): Observable<Etudiant> {
    return this.http.get<Etudiant>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => {
        console.error(`Error fetching student with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  createEtudiant(etudiant: Etudiant): Observable<Etudiant> {
    // Formatage de l'objet avant envoi
    const formattedEtudiant = this.formatEtudiantForServer(etudiant);
    console.log('Creating student with data:', formattedEtudiant);
    
    return this.http.post<Etudiant>(this.apiUrl, formattedEtudiant, this.httpOptions).pipe(
      tap(data => console.log('Created student:', data)),
      catchError(error => {
        console.error('Error creating student:', error);
        return throwError(() => error);
      })
    );
  }

  updateEtudiant(id: number, etudiant: Etudiant): Observable<Etudiant> {
    const formattedEtudiant = this.formatEtudiantForServer(etudiant);
    console.log(`Updating student ${id} with data:`, formattedEtudiant);
    
    return this.http.put<Etudiant>(`${this.apiUrl}/${id}`, formattedEtudiant, this.httpOptions).pipe(
      tap(data => console.log('Updated student:', data)),
      catchError(error => {
        console.error(`Error updating student with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  deleteEtudiant(id: number): Observable<void> {
    console.log(`Deleting student with ID: ${id}`);
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => console.log(`Deleted student with ID: ${id}`)),
      catchError(error => {
        console.error(`Error deleting student with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  // Helper to format data properly for the server
  private formatEtudiantForServer(etudiant: Etudiant): any {
    const formatted = { ...etudiant };
    
    // Ensure cin is a number
    if (typeof formatted.cin === 'string') {
      formatted.cin = parseInt(formatted.cin as unknown as string, 10);
    }
    
    // Make sure dateNaissance is properly formatted (ISO format)
    if (formatted.dateNaissance && typeof formatted.dateNaissance === 'string') {
      // Already a string, keep it as is or format if needed
      if (!formatted.dateNaissance.includes('T')) {
        // Add time component if missing
        formatted.dateNaissance = formatted.dateNaissance + 'T00:00:00.000Z';
      }
    }
    
    // Add empty reservations array if not present
    if (!formatted.reservations) {
      formatted.reservations = [];
    }
    
    return formatted;
  }
}