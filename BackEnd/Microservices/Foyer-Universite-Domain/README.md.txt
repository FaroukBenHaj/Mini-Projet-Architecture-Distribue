# 🏢 Université-Foyer Microservice

This microservice is part of the larger **Foyer Management System**, built using **Spring Boot** in **IntelliJ IDEA**. It manages university residences and implements a **one-to-one relationship** between `Université` and `Foyer`.

---

## ✨ Features

- Full **CRUD operations** for:
  - `Université`
  - `Foyer`
- **One-to-One Mapping** between `Université` and `Foyer`
- Export functionality:
  - 📄 Export university data (with linked foyer) to **Excel** and **PDF**
- Eureka Client enabled for service registration in a microservices ecosystem

---

## 🧱 Architecture Overview

| Layer            | Description                                                      |
|------------------|------------------------------------------------------------------|
| **Entities**     | JPA entities for `Université` and `Foyer`                        |
| **Repositories** | Spring Data JPA interfaces for data access                       |
| **Services**     | Business logic for managing universities and foyers              |
| **Controllers**  | RESTful APIs for CRUD and export operations                      |

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Lombok**
- **Apache POI** (Excel export)
- **iText/OpenPDF** (PDF export)
- **MySQL**
- **Maven**
- **Eureka Client**

---

## 🌐 Postman API Endpoints

> Base URL: `http://localhost:8080`

---

### 🏫 Université Endpoints

#### ✅ Create Université
```http
POST /universites
Content-Type: application/json
Body:

json
Copier
Modifier
{
  "id": 0,
  "nom": "Université de Tunis",
  "adresse": "Tunis",
  "email": "contact@utunis.tn",
  "foyer": {
    "id": 1
  }
}
♻️ Update Université
http
Copier
Modifier
PUT /universites/{id}
Content-Type: application/json
Replace {id} with the Université ID.
Body is similar to Create.

❌ Delete Université
http
Copier
Modifier
DELETE /universites/{id}
📥 Get Université by ID
http
Copier
Modifier
GET /tpfoyer/universites/{id}
Returns a Université and its linked Foyer.

🔁 Alternative Create (namespaced route)
http
Copier
Modifier
POST /tpfoyer/universites
Used for organizing routes logically under /tpfoyer.

📊 Export Universités to Excel
http
Copier
Modifier
GET /tpfoyer/universites/export/excel
🧾 Export Universités to PDF
http
Copier
Modifier
GET /tpfoyer/universites/export/pdf
🏠 Foyer Endpoints
✅ Create Foyer
http
Copier
Modifier
POST /foyers
or:

http
Copier
Modifier
POST /tpfoyer/foyers
♻️ Update Foyer
http
Copier
Modifier
PUT /tpfoyer/foyers/{id}
Content-Type: application/json
❌ Delete Foyer
http
Copier
Modifier
DELETE http://localhost:8089/tpfoyer/foyers/{id}
Note: This endpoint runs on port 8089, not 8080.

📦 Project Context
This microservice is one component of a modular Foyer Management Application. Other microservices may include user authentication, reservation handling, etc. Each service is registered with Eureka for easy discovery and integration via API Gateway or load balancers.

📁 Project Setup
Clone the repository

Ensure MySQL is running

Update application.properties with your DB configs

Start your Eureka Server

Run this microservice from IntelliJ or via mvn spring-boot:run

🧩 Related Services
✅ Eureka Server (Service discovery)

🔐 User Authentication Microservice

🏨 Reservation Microservice

🔍 Notes
The API includes both direct (/universites) and namespaced (/tpfoyer/universites) endpoints for flexibility.

Excel and PDF export functions bundle the university with its linked foyer.

Avoid port conflicts if running multiple services locally.