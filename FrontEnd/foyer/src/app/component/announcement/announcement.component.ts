import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Announcement, AnnouncementType } from 'src/app/models/announcement.model';
import { AnnouncementService } from 'src/app/services/announcement.service';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  announcements: Announcement[] = [];
  announcementForm: FormGroup;
  isEditMode = false;
  currentAnnouncementId: number | null = null;
  isLoading = false;
  searchTerm = '';
  showForm = false;
  successMessage = '';
  errorMessage = '';
  showSuccessPopup = false;
  announcementAddedTitle = '';
  announcementTypes = Object.values(AnnouncementType);

  constructor(private announcementService: AnnouncementService, private fb: FormBuilder) {
    this.announcementForm = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      type: ['', [Validators.required]],
      isActive: [true]
    });
  }

  ngOnInit(): void {
    this.loadAnnouncements();
  }

  loadAnnouncements(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.announcementService.getAllAnnouncements().subscribe({
      next: data => {
        this.announcements = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Erreur lors du chargement des annonces. Veuillez réessayer.';
        setTimeout(() => this.errorMessage = '', 5000);
      }
    });
  }

  onSubmit(): void {
    if (this.announcementForm.invalid) {
      Object.keys(this.announcementForm.controls).forEach(key => {
        const control = this.announcementForm.get(key);
        control?.markAsTouched();
      });
      return;
    }
    this.isLoading = true;
    this.errorMessage = '';
    const announcementData: Announcement = {
      ...this.announcementForm.value
    };
    if (this.isEditMode && this.currentAnnouncementId) {
      this.announcementService.updateAnnouncement(this.currentAnnouncementId, announcementData).subscribe({
        next: updated => {
          this.handleSuccess('Annonce mise à jour avec succès !');
          this.updateAnnouncementInList(updated);
        },
        error: () => this.handleError('Erreur lors de la mise à jour de l\'annonce.')
      });
    } else {
      this.announcementService.createAnnouncement(announcementData).subscribe({
        next: created => {
          this.announcementAddedTitle = created.title;
          this.showSuccessPopup = true;
          setTimeout(() => this.showSuccessPopup = false, 10000);
          this.handleSuccess('Annonce créée avec succès !');
          this.announcements.push(created);
        },
        error: () => this.handleError('Erreur lors de la création de l\'annonce.')
      });
    }
  }

  handleSuccess(message: string): void {
    this.successMessage = message;
    this.isLoading = false;
    this.resetForm();
    setTimeout(() => this.successMessage = '', 5000);
    this.loadAnnouncements();
  }

  handleError(message: string): void {
    this.errorMessage = message;
    this.isLoading = false;
    setTimeout(() => this.errorMessage = '', 5000);
  }

  updateAnnouncementInList(updated: Announcement): void {
    const index = this.announcements.findIndex(a => a.id === updated.id);
    if (index !== -1) {
      this.announcements[index] = updated;
    }
  }

  editAnnouncement(announcement: Announcement): void {
    this.isEditMode = true;
    this.currentAnnouncementId = announcement.id || null;
    this.showForm = true;
    this.announcementForm.patchValue({
      title: announcement.title,
      content: announcement.content,
      type: announcement.type,
      isActive: announcement.isActive
    });
  }

  deleteAnnouncement(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      this.isLoading = true;
      this.announcementService.deleteAnnouncement(id).subscribe({
        next: () => {
          this.handleSuccess('Annonce supprimée avec succès !');
          this.announcements = this.announcements.filter(a => a.id !== id);
        },
        error: () => this.handleError('Erreur lors de la suppression de l\'annonce.')
      });
    }
  }

  deactivateAnnouncement(id: number): void {
    this.isLoading = true;
    this.announcementService.deactivateAnnouncement(id).subscribe({
      next: () => {
        this.handleSuccess('Annonce désactivée avec succès !');
        this.loadAnnouncements();
      },
      error: () => this.handleError('Erreur lors de la désactivation de l\'annonce.')
    });
  }

  resetForm(): void {
    this.announcementForm.reset({ isActive: true });
    this.isEditMode = false;
    this.currentAnnouncementId = null;
    this.showForm = false;
  }

  toggleForm(): void {
    if (this.isEditMode) {
      this.resetForm();
    } else {
      this.showForm = !this.showForm;
    }
  }

  get filteredAnnouncements(): Announcement[] {
    if (!this.searchTerm) {
      return this.announcements;
    }
    const term = this.searchTerm.toLowerCase();
    return this.announcements.filter(a =>
      a.title.toLowerCase().includes(term) ||
      a.content.toLowerCase().includes(term) ||
      a.type.toLowerCase().includes(term)
    );
  }
}
