# Use an official Java runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build files
COPY pom.xml .
COPY src ./src

# Package the application (build inside container)
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/urlshortener-0.0.1-SNAPSHOT.jar"]