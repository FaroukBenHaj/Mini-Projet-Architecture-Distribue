# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the target folder into the container
COPY target/notification-service.jar notification-service.jar

# Expose the port for Notification Service (8080 by default)
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]
