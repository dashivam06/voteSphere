# Build stage - using verified Maven image
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage - using verified Tomcat image
FROM tomcat:10.1.24-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*

# Railway-specific configuration
COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration
ENV CATALINA_OPTS="-Dorg.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar \
                   -Dorg.apache.catalina.startup.TldConfig.jarsToSkip=*.jar \
                   -Dserver.port=${PORT}"

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:${PORT}/ || exit 1

EXPOSE ${PORT}

# Configure and start Tomcat
CMD ["catalina.sh", "run"]
