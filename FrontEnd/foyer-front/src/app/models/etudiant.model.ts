export interface Etudiant {
    idEtudiant?: number;
    nomEt: string;
    prenomEt: string;
    cin: number;
    ecole: string;
    dateNaissance: string; // Changé en string pour correspondre au format ISO
    reservations?: any[]; // Ajouté pour gérer les relations
  }