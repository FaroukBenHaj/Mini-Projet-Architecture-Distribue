import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReservationService, Reservation } from '../../services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  reservations: Reservation[] = [];
  filteredReservations: Reservation[] = [];
  reservationForm: FormGroup;
  searchTerm = '';
  showForm = false;
  isEditMode = false;
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private reservationService: ReservationService
  ) {
    this.reservationForm = this.fb.group({
      idReservation: [null],
      anneeUniversitaire: ['', Validators.required],
      estValide: [false]
    });
  }

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.isLoading = true;
    this.reservationService.getAllReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.applyFilter();
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erreur lors du chargement des réservations.';
        this.isLoading = false;
        setTimeout(() => this.errorMessage = '', 5000);
      }
    });
  }

  applyFilter(): void {
    if (!this.searchTerm) {
      this.filteredReservations = [...this.reservations];
      return;
    }
    
    const searchTermLower = this.searchTerm.toLowerCase();
    this.filteredReservations = this.reservations.filter(reservation => {
      const idString = reservation.idReservation?.toString() || '';
      const dateString = reservation.anneeUniversitaire ? 
        (typeof reservation.anneeUniversitaire === 'string' ? 
          new Date(reservation.anneeUniversitaire) : 
          reservation.anneeUniversitaire).toLocaleDateString() : '';
      const statusString = reservation.estValide ? 'valide' : 'non valide';
      
      return idString.includes(searchTermLower) || 
             dateString.toLowerCase().includes(searchTermLower) ||
             statusString.includes(searchTermLower);
    });
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (!this.showForm) {
      this.resetForm();
    }
  }

  resetForm(): void {
    this.isEditMode = false;
    this.reservationForm.reset({
      idReservation: null,
      anneeUniversitaire: '',
      estValide: false
    });
    this.showForm = false;
  }

  onSubmit(): void {
    if (this.reservationForm.invalid) {
      return;
    }

    const reservation = this.reservationForm.value;
    this.isLoading = true;

    if (this.isEditMode) {
      // Update existing reservation
      this.reservationService.updateReservation(reservation.idReservation, reservation).subscribe({
        next: () => {
          this.successMessage = 'Réservation mise à jour avec succès!';
          setTimeout(() => this.successMessage = '', 5000);
          this.loadReservations();
          this.resetForm();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = 'Erreur lors de la mise à jour de la réservation.';
          setTimeout(() => this.errorMessage = '', 5000);
          this.isLoading = false;
        }
      });
    } else {
      // Create new reservation
      this.reservationService.createReservation(reservation).subscribe({
        next: () => {
          this.successMessage = 'Réservation ajoutée avec succès!';
          setTimeout(() => this.successMessage = '', 5000);
          this.loadReservations();
          this.resetForm();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = 'Erreur lors de l\'ajout de la réservation.';
          setTimeout(() => this.errorMessage = '', 5000);
          this.isLoading = false;
        }
      });
    }
  }

  editReservation(reservation: Reservation): void {
    this.isEditMode = true;
    this.showForm = true;
    this.reservationForm.patchValue({
      idReservation: reservation.idReservation,
      anneeUniversitaire: this.formatDateForInput(reservation.anneeUniversitaire),
      estValide: reservation.estValide
    });
  }

  deleteReservation(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('Êtes-vous sûr de vouloir supprimer cette réservation?')) {
      this.isLoading = true;
      this.reservationService.deleteReservation(id).subscribe({
        next: () => {
          this.successMessage = 'Réservation supprimée avec succès!';
          setTimeout(() => this.successMessage = '', 5000);
          this.loadReservations();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = 'Erreur lors de la suppression de la réservation.';
          setTimeout(() => this.errorMessage = '', 5000);
          this.isLoading = false;
        }
      });
    }
  }

  formatDateForInput(date: Date | string): string {
    if (!date) return '';
    
    const d = typeof date === 'string' ? new Date(date) : date;
    
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
  }
}