
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Etudiant } from '../../models/etudiant.model';
import { EtudiantService } from '../../services/etudiant.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-etudiant',
  templateUrl: './etudiant.component.html',
  styleUrls: ['./etudiant.component.css']
})
export class EtudiantComponent implements OnInit {
  closeSuccessPopup(): void {
    this.showSuccessPopup = false;
    this.resetForm(); // ✅ C’est ici qu’on ferme et reset le formulaire
    this.router.navigate(['/etudiants']);

  }
  
  
  etudiants: Etudiant[] = [];
  etudiantForm: FormGroup;
  isEditMode = false;
  currentEtudiantId: number | null = null;
  isLoading = false;
  searchTerm = '';
  showForm = false;
  successMessage = '';
  errorMessage = '';
  showSuccessPopup = false;
  etudiantAjouteNom = ''; // Pour stocker le nom affiché dans la popup


  constructor(
    private etudiantService: EtudiantService,
    private fb: FormBuilder,
    private router: Router,

  ) {
    this.etudiantForm = this.fb.group({
      nomEt: ['', [Validators.required]],
      prenomEt: ['', [Validators.required]],
      cin: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
      ecole: ['', [Validators.required]],
      dateNaissance: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadEtudiants();
  }

  loadEtudiants(): void {
    this.isLoading = true;
    this.errorMessage = '';
    
    this.etudiantService.getAllEtudiants().subscribe({
      next: (data) => {
        console.log('Etudiants chargés avec succès:', data);
        this.etudiants = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des étudiants:', error);
        this.isLoading = false;
        this.errorMessage = 'Erreur lors du chargement des étudiants. Veuillez réessayer.';
        setTimeout(() => this.errorMessage = '', 5000);
      }
    });
  }

  onSubmit(): void {
    if (this.etudiantForm.invalid) {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      Object.keys(this.etudiantForm.controls).forEach(key => {
        const control = this.etudiantForm.get(key);
        control?.markAsTouched();
      });
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    
    // Créer l'objet étudiant à partir du formulaire
    const etudiantData: Etudiant = {
      ...this.etudiantForm.value,
      // Ajuster le format de la date si nécessaire
      dateNaissance: this.formatDate(this.etudiantForm.value.dateNaissance)
    };

    console.log('Données du formulaire à envoyer:', etudiantData);

    if (this.isEditMode && this.currentEtudiantId) {
      this.etudiantService.updateEtudiant(this.currentEtudiantId, etudiantData).subscribe({
        next: (updatedEtudiant) => {
          this.handleSuccess('Étudiant mis à jour avec succès!');
          this.updateEtudiantInList(updatedEtudiant);
        },
        error: (error) => this.handleError('Erreur lors de la mise à jour de l\'étudiant.', error)
      });
    } else {
      this.etudiantService.createEtudiant(etudiantData).subscribe({
        next: (newEtudiant) => {
          console.log('Nouvel étudiant créé:', newEtudiant);
          this.etudiantAjouteNom = newEtudiant.nomEt;
          this.showSuccessPopup = true;
          setTimeout(() => this.showSuccessPopup = false, 10000);

          this.handleSuccess('Étudiant ajouté avec succès!');
          this.etudiants.push(newEtudiant);
        },
        error: (error) => this.handleError('Erreur lors de la création de l\'étudiant.', error)
      });
      
    }
  }

  // Format date to ISO string (YYYY-MM-DD)
  private formatDate(dateStr: string): string {
    if (!dateStr) return '';
    
    try {
      // If it's already an ISO string, return it
      if (dateStr.includes('T')) return dateStr;
      
      // Otherwise, convert to ISO format
      const date = new Date(dateStr);
      if (isNaN(date.getTime())) {
        console.error('Invalid date:', dateStr);
        return dateStr;
      }
      return date.toISOString().split('T')[0];
    } catch (e) {
      console.error('Error formatting date:', e);
      return dateStr;
    }
  }

  handleSuccess(message: string): void {
    this.successMessage = message;
    this.isLoading = false;
    this.resetForm();
    setTimeout(() => this.successMessage = '', 5000);
    
    // Reload the students list to ensure we have the latest data
    this.loadEtudiants();
  }

  handleError(message: string, error: any): void {
    console.error(message, error);
    this.errorMessage = message;
    if (error.error && error.error.message) {
      this.errorMessage += ' ' + error.error.message;
    }
    this.isLoading = false;
    setTimeout(() => this.errorMessage = '', 5000);
  }

  updateEtudiantInList(updatedEtudiant: Etudiant): void {
    const index = this.etudiants.findIndex(e => e.idEtudiant === updatedEtudiant.idEtudiant);
    if (index !== -1) {
      this.etudiants[index] = updatedEtudiant;
    }
  }

  editEtudiant(etudiant: Etudiant): void {
    this.isEditMode = true;
    this.currentEtudiantId = etudiant.idEtudiant || null;
    this.showForm = true;
    
    // Format the date for form display
    let formattedDate = etudiant.dateNaissance;
    if (typeof formattedDate === 'string' && formattedDate.includes('T')) {
      formattedDate = formattedDate.split('T')[0];
    }
    
    this.etudiantForm.patchValue({
      nomEt: etudiant.nomEt,
      prenomEt: etudiant.prenomEt,
      cin: etudiant.cin,
      ecole: etudiant.ecole,
      dateNaissance: formattedDate
    });
  }

  deleteEtudiant(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant?')) {
      this.isLoading = true;
      this.etudiantService.deleteEtudiant(id).subscribe({
        next: () => {
          this.handleSuccess('Étudiant supprimé avec succès!');
          this.etudiants = this.etudiants.filter(e => e.idEtudiant !== id);
        },
        error: (error) => this.handleError('Erreur lors de la suppression de l\'étudiant.', error)
      });
    }
  }

  resetForm(): void {
    this.etudiantForm.reset();
    this.isEditMode = false;
    this.currentEtudiantId = null;
    this.showForm = false;
  }

  toggleForm(): void {
    if (this.isEditMode) {
      this.resetForm();
    } else {
      this.showForm = !this.showForm;
    }
  }

  get filteredEtudiants(): Etudiant[] {
    if (!this.searchTerm) {
      return this.etudiants;
    }
    const term = this.searchTerm.toLowerCase();
    return this.etudiants.filter(
      etudiant => 
        etudiant.nomEt?.toLowerCase().includes(term) || 
        etudiant.prenomEt?.toLowerCase().includes(term) || 
        etudiant.ecole?.toLowerCase().includes(term) || 
        String(etudiant.cin).includes(term)
    );
  }
}
