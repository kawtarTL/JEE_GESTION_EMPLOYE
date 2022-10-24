# syntax=docker/dockerfile:1
FROM openjdk:8-alpine as base_backend
WORKDIR /app
COPY backend/.mvn/ .mvn
COPY backend/mvnw backend/pom.xml ./
RUN ./mvnw dependency:resolve
COPY backend/src ./src

FROM base_backend as dev_backend
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql"]

FROM node:18-alpine as base_frontend
WORKDIR /app
COPY ["./frontend/package.json", "./frontend/package-lock.json*", "./"]
RUN npm install -g @angular/cli
RUN npm install
COPY ./backend .

FROM base_frontend  as dev_frontend
CMD ["ng", "serve", "--host", "0.0.0.0"]
