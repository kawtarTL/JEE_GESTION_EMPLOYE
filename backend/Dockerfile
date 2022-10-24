# syntax=docker/dockerfile:1
FROM openjdk:8-alpine as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as dev
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql"]
