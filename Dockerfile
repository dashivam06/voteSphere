# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM tomcat:10.1.24-jdk17-temurin

# Remove default ROOT and create webapps directory
RUN rm -rf /usr/local/tomcat/webapps/ROOT* && \
    mkdir -p /usr/local/tomcat/webapps/ROOT

# Copy WAR file and manually extract it
COPY --from=build /app/target/voteSphere.war /tmp/
RUN unzip /tmp/voteSphere.war -d /usr/local/tomcat/webapps/ROOT/ && \
    rm /tmp/voteSphere.war

# Ensure properties exist (add if missing)
RUN if [ ! -f /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/application.properties ]; then \
    echo "Creating default application.properties" && \
    mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/ && \
    echo "server.port=8080" > /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/application.properties; \
    fi

# Environment configuration
ENV CATALINA_OPTS="-Dserver.port=8080 -Dspring.config.location=file:/usr/local/tomcat/conf/"
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Copy external configuration if needed
COPY config/application.properties /usr/local/tomcat/conf/ 2>/dev/null || :

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/ || exit 1

CMD ["catalina.sh", "run"]