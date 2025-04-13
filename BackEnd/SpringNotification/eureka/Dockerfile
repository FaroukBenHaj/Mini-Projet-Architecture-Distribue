# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the target folder into the container
COPY target/eureka-server.jar eureka-server.jar

# Expose the Eureka Server port (8761 by default)
EXPOSE 8761

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
