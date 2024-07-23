# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the project’s pom.xml and Maven wrapper files
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Download the dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the project source code
COPY src ./src

# Build the project
RUN ./mvnw package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "target/loginapp-0.0.1-SNAPSHOT.jar"]
