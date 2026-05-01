

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

# 🚀 Task Management System

A Spring Boot based microservices system for managing tasks using event-driven architecture. 
The system uses Apache Kafka for asynchronous communication, Eureka for service discovery, and API Gateway with JWT authentication.

---

## 🔹 Architecture Overview

Client → API Gateway → Task Service → Kafka → Notification Service

### 🔹 Core Components

- API Gateway – Central entry point with routing & JWT authentication  
- Task Service – Handles task creation & business logic  
- Notification Service – Consumes Kafka events and processes notifications  
- Eureka Server – Service discovery & registration  
- Admin Server – Monitoring and management  
- Kafka + Zookeeper – Event streaming backbone  
- PostgreSQL – Persistent storage  

---

## 🔹 Tech Stack

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Security (JWT Authentication)
- Apache Kafka
- PostgreSQL
- Docker & Docker Compose

---

## 🔹 Getting Started

### 1️⃣ Build Services

bash mvn clean package -DskipTests 

---

### 2️⃣ Start Entire System

bash docker-compose up -d 

---

### 3️⃣ Stop Services

bash docker-compose down 

---

## ⚠️ Important Configuration Notes

### Kafka:
kafka:9092

### PostgreSQL:
jdbc:postgresql://postgres-db:5432/Task_db

> ❗ Important:  
> Docker containers cannot use localhost to communicate with each other.  
> Always use service names defined in docker-compose.yml.

---

## 🔹 Kafka Topics

- task-created → Main event topic  
- task-created-dlt → Dead Letter Topic for failed events  

---

## 🔹 Request Flow

1. Client sends request to API Gateway
2. Gateway validates JWT token
3. Request is routed to Task Service
4. Task Service publishes event to Kafka
5. Notification Service consumes the event and processes it

---

## 📁 Project Structure

task-service/ 
notification-service/ 
api-gateway/ 
eureka-server/ 
admin-server/ 
docker-compose.yml
Jenkinsfile

---

## 🐳 Running Individual Service

Example:

bash docker run --name task-service \ --network task-network \ -p 8081:8081 \ 
-e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \ task-service:latest 

---

## 🔹 Key Design Highlights

- Event-driven architecture using Kafka  
- Decoupled microservices for scalability  
- Centralized authentication via JWT  
- Service discovery with Eureka  
- Containerized deployment using Docker  

---

## 📌 Notes

- All containers must be on the same Docker network
- Ensure Kafka is running before services start
- Use service names instead of localhost for inter-service communication
