FROM openjdk:21-jdk-slim
COPY target/*.jar kantor.jar
EXPOSE 8089
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "kantor.jar"]
