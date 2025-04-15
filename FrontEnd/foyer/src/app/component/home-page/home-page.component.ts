import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

interface CardInfo {
  title: string;
  description: string;
  icon: string;
  route: string;
  color: string;
}

interface Statistic {
  title: string;
  value: number;
  icon: string;
}

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  cards: CardInfo[] = [
    {
      title: 'Gestion des Étudiants',
      description: 'Gérez les informations des étudiants et leurs dossiers universitaires.',
      icon: 'fas fa-user-graduate',
      route: '/etudiants',
      color: 'var(--primary-color)'
    },
    {
      title: 'Gestion des Foyers',
      description: 'Administrez les foyers universitaires et leurs caractéristiques.',
      icon: 'fas fa-building',
      route: '/foyers',
      color: 'var(--secondary-color)'
    },
    {
      title: 'Gestion des Blocs',
      description: 'Organisez les différents blocs et leur capacité d\'accueil.',
      icon: 'fas fa-cubes',
      route: '/blocs',
      color: 'var(--accent-color)'
    },
    {
      title: 'Gestion des Finances',
      description: 'Faites vos payments en toute simplicité',
      icon: 'fas fa-cubes',
      route: '/paiements',
      color: 'var(--accent-color)'
    },
    {
      title: 'Gestion des Chambres',
      description: 'Administrez les chambres disponibles et leurs types.',
      icon: 'fas fa-bed',
      route: '/chambres',
      color: 'var(--warning-color)'
    },
    {
      title: 'Gestion des Réservations',
      description: 'Gérez les demandes et attributions de logements.',
      icon: 'fas fa-calendar-check',
      route: '/reservations',
      color: 'var(--info-color)'
    },
    {
      title: 'Gestion des Universités',
      description: 'Administrez les universités partenaires.',
      icon: 'fas fa-university',
      route: '/universites',
      color: 'var(--error-color)'
    }
  ];

  statistics: Statistic[] = [
    { title: 'Étudiants', value: 1250, icon: 'fas fa-user-graduate' },
    { title: 'Foyers', value: 8, icon: 'fas fa-building' },
    { title: 'Chambres', value: 450, icon: 'fas fa-bed' },
    { title: 'Réservations', value: 380, icon: 'fas fa-calendar-check' }
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}