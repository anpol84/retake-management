FROM openjdk:17
FROM maven:3.8.1-openjdk-17

WORKDIR /app

COPY . .

RUN mvn clean package

EXPOSE 80

CMD ["java", "-jar", "/app/target/retakesManagement-0.0.1-SNAPSHOT.jar"]