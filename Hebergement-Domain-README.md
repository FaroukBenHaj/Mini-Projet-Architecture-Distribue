# 🏨 Hebergement Domain Microservice

## 🌟 Overview
This microservice manages hostel accommodation blocks (Blocs) and rooms (Chambres) with their relationships. It provides basic CRUD operations for both entities through REST APIs.

## 🏛️ Entities

### 🏢 Bloc
- Represents a hostel block/building
- **Attributes**:
    - `idBloc`: Long (auto-generated) 🔑
    - `nomBloc`: String (name of the block) 🏷️
    - `capaciteBloc`: Long (capacity of the block) 🧑‍🤝‍🧑
    - `myChambre`: Set<Chambre> (rooms in this block) �

### 🚪 Chambre
- Represents a room in a hostel block
- **Attributes**:
    - `idChambre`: Long (auto-generated) 🔑
    - `numeroChambre`: Long (room number) 🔢
    - `typeC`: TypeChambre (room type enum) 🛌
    - `bloc`: Bloc (the block this room belongs to) ↔️

### 🎚️ TypeChambre (Enum)
Possible room types:
- `Simple` (1 bed) 🛏️
- `Double` (2 beds) 🛏️🛏️
- `Triple` (3 beds) 🛏️🛏️🛏️

## 🌐 API Endpoints

### 🏗️ Bloc Controller (`/Bloc`)
- `GET /show_list` 👀: Get all blocks
- `POST /add_Bloc` ➕: Add a new block

### 🚪 Chambre Controller (`/Chambre`)
*(Endpoints to be implemented) 🚧*

## 💾 Repository Interfaces
- `BlocRepository` 📦: JPA repository for Bloc entities
- `ChambreRepository` 📦: JPA repository for Chambre entities

## ⚙️ Service Layers
- `BlocService` 🛠️: Implements business logic for Bloc operations
- `ChambreService` 🛠️: Implements business logic for Chambre operations

## 🛠️ Setup & Installation
1. Ensure you have Java JDK and Maven installed ☕
2. Clone the repository 📥
3. Configure your database connection in `application.properties` ⚙️
4. Build the project: `mvn clean install` 🔨
5. Run the application: `mvn spring-boot:run` 🚀

## 📦 Dependencies
- Spring Boot 🌱
- Spring Data JPA 🗄️
- Lombok (recommended for future use) ✨

## 🔮 Future Enhancements
1. Implement all CRUD operations for both Bloc and Chambre ✅
2. Add validation for entities 🔍
3. Implement exception handling ⚠️
4. Add Swagger documentation 📄
5. Implement DTOs for API responses 🔄
6. Add unit and integration tests 🧪

## 🗄️ Database Schema
The application will automatically generate tables based on the entities:
- `Bloc` table with one-to-many relationship to `Chambre` ↔️
- `Chambre` table with many-to-one relationship to `Bloc` ↔️

## 🚦 Status
🔴 **Work in Progress** - Basic structure implemented, needs expansion

## 👨‍💻 Contributors
Mohamed Farouk Ben Haj Amor  🤓

💡 **Note**: The current implementation is a basic structure that needs to be expanded with complete CRUD operations and additional features.
