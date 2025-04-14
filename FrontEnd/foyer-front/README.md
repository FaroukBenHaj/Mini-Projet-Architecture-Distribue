# Campus Logement - Interface d'Administration

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

Une application web moderne développée avec Angular pour la gestion complète des logements universitaires. Cette interface d'administration permet de gérer les étudiants, les foyers, les blocs, les chambres, les réservations et les universités dans un système de logement universitaire.

## 📋 Table des matières

- [Présentation](#présentation)
- [Technologies utilisées](#technologies-utilisées)
- [Captures d'écran](#captures-décran)
- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Structure du projet](#structure-du-projet)
- [Intégration avec le backend](#intégration-avec-le-backend)
- [Développement](#développement)
- [Déploiement](#déploiement)
- [Contributeurs](#contributeurs)

## 🎯 Présentation

**Campus Logement** est une application conçue pour simplifier la gestion administrative des logements universitaires. Avec une interface utilisateur moderne inspirée d'iOS et un design "Blue Sky", l'application offre une expérience utilisateur intuitive et agréable.

Cette interface frontend communique avec une API REST développée en Spring Boot pour la gestion des données.

## 🛠️ Technologies utilisées

- **Angular** 17+ - Framework frontend
- **TypeScript** - Langage de programmation
- **RxJS** - Bibliothèque pour la programmation réactive
- **Angular Forms** - Pour la gestion des formulaires et validations
- **HttpClient** - Pour les communications avec l'API REST
- **Font Awesome** - Pour les icônes
- **CSS3** avec variables CSS personnalisées - Pour le design responsive

## 📸 Captures d'écran

*Vous trouverez ci-dessous quelques captures d'écran de l'application :*

- **Page d'accueil** - Page d'accueil avec présentation des fonctionnalités et statistiques
- **Gestion des étudiants** - Interface CRUD pour les étudiants
- **Gestion des foyers** - Interface CRUD pour les foyers
- **Gestion des blocs** - Interface CRUD pour les blocs
- **Gestion des chambres** - Interface CRUD pour les chambres
- **Gestion des réservations** - Interface CRUD pour les réservations
- **Gestion des universités** - Interface CRUD pour les universités

## ✨ Fonctionnalités

### Générales
- Design responsive s'adaptant à tous les types d'écrans
- Navigation intuitive avec menu responsive
- Système de notification pour les actions réussies ou les erreurs
- Animations et transitions pour une expérience utilisateur fluide
- Recherche et filtrage en temps réel

### Spécifiques
- **Gestion des étudiants** : Ajout, modification, suppression et recherche d'étudiants
- **Gestion des foyers** : Administration complète des foyers universitaires
- **Gestion des blocs** : Organisation des blocs au sein des foyers
- **Gestion des chambres** : Administration des chambres avec filtres par type
- **Gestion des réservations** : Suivi des demandes et attributions de logements
- **Gestion des universités** : Administration des universités partenaires

## 📋 Prérequis

Pour exécuter ce projet, vous aurez besoin de :

- Node.js (v16+)
- npm (v8+) ou yarn (v1.22+)
- Angular CLI (v17+)
- Backend Spring Boot en cours d'exécution (voir section intégration backend)

## 🚀 Installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/votre-organisation/campus-logement.git
   cd campus-logement
   ```

2. **Installer les dépendances**
   ```bash
   npm install
   # ou
   yarn install
   ```

3. **Configurer l'URL du backend**
   
   Ouvrez `src/environments/environment.ts` et ajustez l'URL de l'API si nécessaire :
   ```typescript
   export const environment = {
     production: false,
     apiUrl: 'http://localhost:8080/api'
   };
   ```

4. **Lancer l'application**
   ```bash
   ng serve
   ```

5. **Accéder à l'application**
   
   Ouvrez votre navigateur et accédez à `http://localhost:4200`

## 📁 Structure du projet

```
campus-logement/
├── src/
│   ├── app/
│   │   ├── component/
│   │   │   ├── bloc/                 # Gestion des blocs
│   │   │   ├── chambre/              # Gestion des chambres
│   │   │   ├── etudiant/             # Gestion des étudiants
│   │   │   ├── foyer/                # Gestion des foyers
│   │   │   ├── home-page/            # Page d'accueil
│   │   │   ├── reservation/          # Gestion des réservations
│   │   │   ├── shared/               # Composants partagés (navbar, footer)
│   │   │   └── universite/           # Gestion des universités
│   │   │
│   │   ├── models/                   # Interfaces et modèles de données
│   │   ├── services/                 # Services pour les communications API
│   │   │
│   │   ├── app.component.*           # Composant racine
│   │   ├── app.module.ts             # Module principal
│   │   └── app-routing.module.ts     # Configuration des routes
│   │
│   ├── assets/                       # Ressources statiques
│   ├── environments/                 # Configuration par environnement
│   ├── index.html                    # Page HTML principale
│   ├── main.ts                       # Point d'entrée
│   └── styles.css                    # Styles globaux
│
├── angular.json                      # Configuration Angular
├── package.json                      # Dépendances
└── README.md                         # Ce document
```

## 🔄 Intégration avec le backend

L'application communique avec un backend Spring Boot exposant une API REST. Les endpoints utilisés sont :

- Étudiants: `http://localhost:8080/api/etudiants`
- Foyers: `http://localhost:8080/api/foyers`
- Blocs: `http://localhost:8080/api/blocs`
- Chambres: `http://localhost:8080/api/chambres`
- Réservations: `http://localhost:8080/api/reservations`
- Universités: `http://localhost:8080/api/universites`

Assurez-vous que le backend est en cours d'exécution avant de lancer l'application Angular.

Pour plus d'information sur le backend, consultez le dépôt Spring Boot correspondant.

## 💻 Développement

### Création d'un nouveau composant

```bash
ng generate component component/nom-du-composant
```

### Création d'un nouveau service

```bash
ng generate service services/nom-du-service
```

### Création d'un nouveau modèle

```bash
ng generate interface models/nom-du-modele
```

### Exécution des tests

```bash
ng test               # Tests unitaires
ng e2e                # Tests end-to-end
```

### Vérification du linting

```bash
ng lint
```

## 📦 Déploiement

### Construction pour la production

```bash
ng build --prod
```

Les fichiers générés seront disponibles dans le dossier `dist/`.

### Déploiement sur un serveur

Le projet peut être déployé sur n'importe quel serveur web statique. Vous pouvez utiliser :

- **Apache**
- **Nginx**
- **Firebase Hosting**
- **Netlify**
- **Vercel**
- **GitHub Pages**

Exemple de déploiement avec Firebase :

```bash
npm install -g firebase-tools
firebase login
firebase init
firebase deploy
```

## 👥 Contributeurs

Ce projet a été développé dans le cadre d'un projet académique à Esprit pour la gestion des logements universitaires.

---

© 2025 Campus Logement. Tous droits réservés.