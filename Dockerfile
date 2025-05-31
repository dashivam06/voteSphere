# ========== Stage 1: Build the WAR file ==========
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ========== Stage 2: Run with Tomcat ==========
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
