# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the target folder into the container
COPY target/gateway-service.jar gateway-service.jar

# Expose the port for Gateway (8081 by default)
EXPOSE 8081

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]
