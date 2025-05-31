# Build stage
FROM --platform=linux/amd64 maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Ensure properties file is copied during build
RUN mkdir -p src/main/resources && \
    test -f src/main/resources/application.properties || \
    echo "Creating default properties" > src/main/resources/application.properties
RUN mvn clean package -DskipTests

# Runtime stage
FROM --platform=linux/amd64 tomcat:10.1.24-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy WAR file
COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration
ENV CATALINA_OPTS="-Dserver.port=8080 -Dspring.config.location=classpath:/,file:/usr/local/tomcat/conf/"
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Copy properties file to external location (if needed)
RUN mkdir -p /usr/local/tomcat/conf && \
    touch /usr/local/tomcat/conf/application.properties

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/ || exit 1

CMD ["catalina.sh", "run"]