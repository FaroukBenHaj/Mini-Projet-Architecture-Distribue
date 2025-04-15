import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../../services/payement.service';
import { Payment } from '../../models/payment.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-payment',
  templateUrl: './payement.component.html',
  styleUrls: ['./payement.component.css']
})
export class PaymentComponent implements OnInit {
  payments: Payment[] = [];
  successMessage: string = '';
  errorMessage: string = '';
  showForm = false;
  paymentForm!: FormGroup;
  isEditMode = false;
  currentPaymentId: number | null = null;  // Ensure it's initialized to null
  searchTerm: string = '';
  selectedStatus: string = '';
  showPopup = false;


  constructor(private paymentService: PaymentService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.getPayments();
    this.paymentForm = this.fb.group({
      montant: ['', [Validators.required]],
      datePaiement: ['', [Validators.required]],
      status: ['', [Validators.required]]
    });
  }

  getPayments(): void {
    this.paymentService.getAllPayments().subscribe(
      data => {
        this.payments = data;
      },
      error => {
        this.errorMessage = 'Error fetching payments.';
      }
    );
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
  
    if (this.showForm && !this.isEditMode) {
      this.showPopup = true; // ✅ Affiche la popup personnalisée
    }
  
    // Réinitialise le mode modification si on annule
    if (!this.showForm) {
      this.isEditMode = false;
    }
  
    this.isEditMode = false;
    this.paymentForm.reset();
    this.successMessage = '';
    this.errorMessage = '';
  }
  
  closePopup(): void {
    this.showPopup = false;
  }
  
  // Add a new payment
  addPayment(): void {
    const payment: Payment = this.paymentForm.value;
    this.paymentService.createPayment(payment).subscribe(
      data => {
        this.successMessage = 'Payment added successfully!';
        this.getPayments();
        this.toggleForm();
      },
      error => {
        this.errorMessage = 'Error adding payment.';
      }
    );
  }

 // Edit an existing payment
editPayment(payment: Payment): void {
  this.showForm = true; // Show the form
  this.isEditMode = true; // Set the form mode to "edit"
  
  // Patch the form with the payment data
  this.paymentForm.patchValue({
    montant: payment.montant,
    datePaiement: payment.datePaiement,
    status: payment.status
  });
  
  // Ensure the payment ID is set correctly for later use
  this.currentPaymentId = payment.idPayment || null;  // Use null if ID is falsy (e.g., 0, undefined)
}

// Update the payment with the current payment ID
updatePayment(): void {
  // Get the updated values from the form
  const updatedPayment: Payment = this.paymentForm.value;

  // Ensure the ID exists before sending the request
  if (this.currentPaymentId) {
    this.paymentService.updatePayment(this.currentPaymentId, updatedPayment).subscribe(
      (data) => {
        this.successMessage = 'Payment updated successfully!';
        this.getPayments();  // Refresh the list of payments
        this.toggleForm();   // Hide the form
      },
      (error) => {
        this.errorMessage = 'Error updating payment.';
        console.error('Error updating payment with ID', this.currentPaymentId, error);
      }
    );
  } else {
    this.errorMessage = 'Invalid payment ID!';
    console.error('Invalid payment ID:', this.currentPaymentId);
  }
}

// Delete a payment by its ID
deletePayment(id: number): void {
  console.log(id);
  // Ensure a valid ID before proceeding with the deletion
  if (id != null) {
    // Ask for confirmation before deleting
    if (confirm('Êtes-vous sûr de vouloir supprimer ce paiement?')) {
      this.paymentService.deletePayment(id).subscribe(
        () => {
          this.successMessage = 'Payment deleted successfully!';
          // Remove the deleted payment from the list locally
          this.payments = this.payments.filter(p => p.idPayment !== id);
        },
        (error) => {
          this.errorMessage = 'Error deleting payment.';
          console.error('Error deleting payment with ID', id, error);
        }
      );
    }
  } else {
    this.errorMessage = 'Invalid payment ID!';
    console.error('Invalid payment ID:', id);  // Log if the ID is invalid
  }
}


  // Handle form submission
  onSubmit(): void {
    if (this.paymentForm.invalid) return;

    if (this.isEditMode) {
      this.updatePayment();
    } else {
      this.addPayment();
    }
  }
  get filteredPayments() {
    return this.payments.filter(payment => 
      (payment.montant.toString().includes(this.searchTerm) || 
      payment.datePaiement.includes(this.searchTerm) || 
      payment.status.includes(this.searchTerm)) &&
      (this.selectedStatus ? payment.status === this.selectedStatus : true)
    );
  }
}
