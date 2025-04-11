# Use OpenJDK 17 base image
FROM openjdk:17

# Expose the application port
EXPOSE 8020

# Add the JAR file into the container
ADD target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

# Specify the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/gateway.jar"]
