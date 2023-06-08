FROM openjdk:17-alpine
# Set the working directory
WORKDIR /app
# Copy the application JAR file
COPY /target/olx-0.0.1-SNAPSHOT.jar .
# Expose the application port
EXPOSE 8080
CMD ["java","-jar","olx-0.0.1-SNAPSHOT.jar"]
