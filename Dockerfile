# Builder

FROM maven:alpine AS builder

WORKDIR /builder

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn package

# App

FROM openjdk:8-alpine AS app

WORKDIR /app

EXPOSE 8080

COPY --from=builder /builder/target/dolares-de-confianza.jar .

CMD ["java", "-jar", "dolares-de-confianza.jar"]
