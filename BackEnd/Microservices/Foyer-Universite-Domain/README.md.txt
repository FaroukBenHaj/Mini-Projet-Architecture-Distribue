# UNIVERSITY HOUSING MANAGEMENT SYSTEM
A comprehensive Spring Boot + Angular application for managing university housing (foyers) and their associations with universities.

████████████████████████████████████████████████████
█                KEY FEATURES                     █
████████████████████████████████████████████████████

== BACKEND (Spring Boot) ==

* University Management:
  - CRUD operations for universities
  - Filter by name, city, country or email
  - Export data to Excel and PDF formats
  - Assign universities to housing facilities

* Housing (Foyer) Management:
  - CRUD operations for student housing
  - Advanced capacity-based queries
  - Statistics and analytics
  - Assign housing to universities

* Statistics & Analytics:
  - Housing count per city
  - Total capacity per city
  - Capacity range distribution
  - Summary statistics (avg/min/max)

* AI Assistant:
  - Natural language processing
  - Ollama/Mistral integration
  - Smart fact-based responses
  - Nearby city suggestions

== FRONTEND (Angular) ==
- Modern responsive UI
- Interactive data visualizations
- AI assistant interface
- Mobile-friendly design

████████████████████████████████████████████████████
█              TECHNOLOGY STACK                   █
████████████████████████████████████████████████████

>> Backend:
- Framework: Spring Boot 3.x
- Database: JPA/Hibernate + Mysql
- AI Integration: Ollama/Mistral
- Documentation: SpringDoc OpenAPI
- Reporting: Apache POI (Excel), iText (PDF)

>> Frontend:
- Angular 15+
- Angular Material
- Chart.js
- RxJS

████████████████████████████████████████████████████
█                API ENDPOINTS                    █
████████████████████████████████████████████████████

=== Universities ===
GET    /universites               - List all universities
GET    /universites/{id}          - Get university by ID  
POST   /universites               - Create new university
PUT    /universites/{id}          - Update university
DELETE /universites/{id}          - Delete university
GET    /universites/filter        - Filter universities
GET    /universites/export/excel  - Export to Excel
GET    /universites/export/pdf    - Export to PDF
POST   /universites/{id}/assign-foyer/{foyerId} - Assign to foyer

=== Housing (Foyers) ===
GET    /foyers                    - List all foyers
GET    /foyers/{id}               - Get foyer by ID
POST   /foyers                    - Create new foyer  
PUT    /foyers/{id}               - Update foyer
DELETE /foyers/{id}               - Delete foyer
POST   /foyers/{id}/assign-universite/{universiteId} - Assign to university

=== Statistics ===
GET /api/statistics/foyer-count-per-city          - Housing count by city
GET /api/statistics/total-capacity-per-city       - Total capacity by city  
GET /api/statistics/capacity-range-distribution   - Capacity ranges
GET /api/statistics/summary-statistics            - Summary stats

=== AI Assistant ===
POST /api/assistant/ask - Submit natural language query

████████████████████████████████████████████████████
█               INSTALLATION                      █
████████████████████████████████████████████████████

>> Backend Setup:
1. Install Java 17+
2. Configure database in application.properties 
3. Run: mvn spring-boot:run

>> Frontend Setup:
1. Install Node.js 16+
2. Run: npm install
3. Run: ng serve

████████████████████████████████████████████████████
█             DATABASE SCHEMA                     █
████████████████████████████████████████████████████

UNIVERSITE (1) ----- (1) FOYER

Universite Fields:
- id (PK)
- nom
- ville  
- pays
- email

Foyer Fields:
- id (PK)
- nom
- capacite
- universite_id (FK)
████████████████████████████████████████████████████
█                POSTMAN GUIDE                     █
████████████████████████████████████████████████████

Base URL: http://localhost:8089/tpfoyer

Example Requests:

Create University:

POST /universites
Content-Type: application/json

{
  "nom": "ESPRIT",
  "ville": "Ariana",
  "pays": "Tunisie",
  "email": "contact@esprit.tn"
}

Advanced Housing Filter:

GET /foyers/advanced-filter?search=Central&capacite=150

AI Query:

POST /api/assistant/ask

Content-Type: application/json

{
  "question": "Show foyers in Sousse with capacity over 100"
}

Tips:

-Use environment variables: {{baseUrl}} = http://localhost:8089/tpfoyer

-Replace placeholders ({id}) with actual values

-Use dynamic variables: {{$randomCity}}, {{$randomEmail}}
████████████████████████████████████████████████████
█                AI EXAMPLES                      █
████████████████████████████████████████████████████

The AI assistant understands queries like:
- "Show foyers in Sousse with capacity over 100"
- "What's the average capacity of foyers?"
- "Compare Tunisian foyer capacities internationally"  
- "What housing options exist near Mahdia?"

████████████████████████████████████████████████████
█             SCHOOL & CONTRIBUTOR                █
████████████████████████████████████████████████████

SCHOOL: ESPRIT School of Engineering

CONTRIBUTOR: Nada Louhichi

