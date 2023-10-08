# Use a base image with a compatible version of Java (e.g., OpenJDK)
FROM openjdk:11

# Set the working directory within the container
WORKDIR /app

# Copy your application JAR file and any necessary dependencies into the container
COPY target/scala-2.13/monster-generator-server_2.13-1.0.jar /app/
# Add any additional dependencies or resources as needed

# Expose the port that your Akka HTTP server listens on (replace with your actual port)
EXPOSE 8080

# Define the command to run your Scala application
CMD ["java", "-jar", "monster-generator-server_2.13-1.0.jar"]