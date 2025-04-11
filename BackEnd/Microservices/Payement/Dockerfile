# Use OpenJDK 17 base image
FROM openjdk:17

# Expose the application port
EXPOSE 8081

# Add the JAR file into the container
ADD target/demo-0.0.1-SNAPSHOT.jar Payement.jar

# Specify the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/Payement.jar"]
