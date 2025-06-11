FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY .mvn .mvn/
COPY mvnw .
COPY pom.xml .

RUN ./mvnw dependency:go-offline

COPY src/ src/

RUN ./mvnw clean package -DskipTests

#---------------------------------------------
# FROM eclipse-temurin:21-jre-alpine AS dev
# WORKDIR /app
#
# COPY target/app-0.0.1-SNAPSHOT.jar app.jar
#
# EXPOSE 8080
#
# CMD ["java", "-jar", "app.jar"]

#---------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS prod
WORKDIR /app

COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
