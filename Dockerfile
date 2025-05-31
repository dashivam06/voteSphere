# Build stage
FROM maven:3.8.6-jdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM tomcat:10.1.40-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file (renamed to ROOT.war for root context)
COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration for Railway
ENV CATALINA_OPTS="-Dorg.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar \
                   -Dorg.apache.catalina.startup.TldConfig.jarsToSkip=*.jar \
                   -Dserver.port=$PORT"

# Health check (recommended for Railway)
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:$PORT/ || exit 1

# Railway automatically sets $PORT (usually 8080)
EXPOSE $PORT

# Configure and start Tomcat
CMD sed -i "s/port=\"8080\"/port=\"$PORT\"/" /usr/local/tomcat/conf/server.xml && \
    catalina.sh run