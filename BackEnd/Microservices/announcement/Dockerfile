# Use a slim OpenJDK 17 base image
FROM openjdk:17-slim

# Where the app will run
EXPOSE 8086

# Copy your built jar into the image
ARG JAR_FILE=target/announcement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
