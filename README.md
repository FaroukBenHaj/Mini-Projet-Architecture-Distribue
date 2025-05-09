# README - Gestion de Logement Universitaire 🏫

## Description 📝
Ce projet est une application de gestion des logements universitaires, basée sur une architecture microservices. Il permet la gestion des foyers, des blocs, des chambres et des réservations pour les étudiants. 👨‍🎓🏠

## Architecture 🏗️
L'application suit une approche basée sur les microservices et comprend plusieurs entités :
- **Université 🎓** : Gère les informations des universités.
- **Foyer 🏢** : Représente un foyer universitaire contenant plusieurs blocs.
- **Bloc 📦** : Une division du foyer contenant des chambres.
- **Chambre 🛏️** : Une pièce dans un bloc, avec différents types (simple, double, triple).
- **Étudiant 👨‍🎓** : Un utilisateur de l'application qui peut réserver des chambres.
- **Réservation 📅** : Une demande d'hébergement faite par un étudiant.
- **Paiement 💳** : Gestion des transactions liées aux réservations.
- **Notification 🔔** : Envoi d'e-mails et d'alertes aux étudiants.

## Technologies Utilisées 🛠️
- **Backend** : Spring Boot, Spring Cloud (pour microservices), Spring Data JPA
- **Frontend** : Angular
- **Base de données** : H2 , SQL 
- **Authentification** : Keycloak
- **Communication interservices** : REST API, Kafka (pour événements asynchrones)

## Installation et Exécution 🚀
### Prérequis
- Java 17+
- Spring boot
- Node.js et Angular CLI
- PostgreSQL
- Docker (optionnel pour déploiement avec conteneurs)

### Étapes
1. **Cloner le dépôt** :
   ```sh
   git clone https://github.com/FaroukBenHaj/Mini-Projet-Architecture-Distribue.git
   cd Mini-Projet-Architecture-Distribue

## Auteurs 👥
- **Badiaa Bouhdid** - Tuteur
- **Algorythme** - Nom De l'equipe

## Profils LinkedIn 🔗
Ajoutez ici vos profils LinkedIn :
- [Badiaa Bouhdid](https://www.linkedin.com/in/badiabouhdid/)

- [Moahmed Farouk Ben Haj Amor ](https://www.linkedin.com/in/mohamed-farouk-ben-haj-amor/)
- [ Safa Ben mustpha ](https://www.linkedin.com/in/safa-ben-mustapha-a54989226/)
- [Maissa Hasni ](https://www.linkedin.com/in/maissa-hasni-380248241/)
- [Hamza Chater  ](https://www.linkedin.com/in/profilcollaborateur)
- [Nada Louhichi ](https://www.linkedin.com/in/nada-louhichi/)
- [Zeineb Daghfous](https://www.linkedin.com/in/profilcollaborateur)



