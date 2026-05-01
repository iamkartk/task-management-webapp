


Client
   |
   v
API Gateway (JWT)
   |
   +----------------------+
   |                      |
   v                      v
Task Service        Notification Service
   |                      ^
   |                      |
   v                      |
 Kafka -------------------+
   |
   v
PostgreSQL

All services registered with Eureka Server
Admin Server monitors services via Eureka