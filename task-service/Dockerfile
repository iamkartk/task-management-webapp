# Step 1: base image
FROM eclipse-temurin:17-jdk-jammy

# Step 2: working directory
WORKDIR /TaskApp

# Step 3: copy jar file
COPY target/task-service-0.0.1-SNAPSHOT.jar TaskApp.jar

# Step 4: expose port
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "TaskApp.jar"]