# Microservice de Réservation 📅

## 📝 Introduction
Ce microservice fait partie d'une application de gestion des logements universitaires basée sur une architecture microservices. Il gère spécifiquement les réservations des chambres pour les étudiants. L'écosystème complet permet la gestion des foyers, des blocs, des chambres et des réservations universitaires. 👨‍🎓🏠
## 🧱 Structure du Modèle de Données

### Entité Reservation ✨
L'entité Reservation contient les attributs suivants :
- `idReservation` : Identifiant unique de la réservation (clé primaire auto-générée)
- `anneeUniversitaire` : Date représentant l'année universitaire concernée
- `estValide` : Indicateur booléen qui détermine si la réservation est validée ou non

## 🌐 API REST

Le microservice expose les endpoints suivants :

- POST `/reservations/create` : Créer une nouvelle réservation
- GET `/reservations/list` : Lister toutes les réservations
- GET `/reservations/{id}` : Obtenir les détails d'une réservation spécifique
- PUT `/reservations/{id}` : Mettre à jour une réservation existante
- DELETE `/reservations/{id}` : Supprimer une réservation
- GET `/reservations/stats/annee` : Obtenir des statistiques sur les réservations par année
- GET `/reservations/export-csv` : Exporter les réservations validées au format CSV

## Fonctionnalités Principales

### 1. Gestion CRUD des Réservations
- Création de nouvelles réservations
- Consultation de réservations existantes
- Mise à jour des informations de réservation
- Suppression de réservations

### 2. Fonctionnalités Additionnelles 🚀
- **Statistiques par Année** : Analyse du nombre de réservations par année universitaire
- **Export CSV** : Possibilité d'exporter les réservations validées au format CSV

## 🤖 Intelligence Artificielle
Notre microservice intègre des fonctionnalités d'IA pour optimiser le processus de réservation :

### Système de Recommandation
- Analyse des préférences des étudiants basée sur leurs réservations précédentes
- Suggestions de logements personnalisées selon les habitudes et besoins

### Prédiction de Disponibilité
- Utilisation d'algorithmes prédictifs pour anticiper les taux d'occupation
- Planification optimisée des capacités d'hébergement par année universitaire

### Validation Automatique
- Système de validation intelligent des réservations basé sur des critères prédéfinis
- Réduction du temps de traitement des demandes de réservation

## 🔧 Service Layer

Le `ReservationService` implémente la logique métier suivante :
- Gestion complète du cycle de vie des réservations (création, consultation, mise à jour, suppression)
- Génération de statistiques par année universitaire
- Export des réservations validées au format CSV

## 🔄 Intégration
Ce microservice s'intègre avec les autres composants de l'application de gestion des logements universitaires pour offrir une solution complète et cohérente de gestion des réservations pour les étudiants.
