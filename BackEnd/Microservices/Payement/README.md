Microservice : Payement 💳
Le microservice Payment gère l'ensemble des transactions financières liées aux réservations de logements universitaires. Il est responsable de l'enregistrement des paiements effectués par les étudiants pour leurs réservations de chambres et assure la communication avec les autres microservices pour le suivi et la gestion des paiements. Ce microservice utilise des mécanismes de sécurité pour garantir la confidentialité des informations bancaires des utilisateurs et assure une intégration fluide avec des systèmes de paiement externes.

Fonctionnalités :
Traitement des paiements : Enregistrement et traitement des paiements effectués lors de la réservation d'un logement.

Gestion des transactions : Suivi des transactions et gestion des statuts de paiement.

Communication interservices : Échange de données avec le microservice de réservation pour valider le paiement avant d'autoriser l'attribution de la chambre.

Sécurité : Intégration avec des systèmes de paiement sécurisés et utilisation des meilleures pratiques de sécurité pour le stockage et la gestion des informations sensibles.

Technologies :
Spring Boot : Pour la gestion des services backend et des API REST.

Spring Cloud : Pour la gestion des microservices.

PostgreSQL : Base de données relationnelle utilisée pour stocker les informations des paiements.

Keycloak : Pour l'authentification et la gestion des utilisateurs.