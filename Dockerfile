# Build stage
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM maven:3.9.2-openjdk-17
RUN rm -rf /usr/local/tomcat/webapps/*

# Railway-specific changes:
# 1. Use PORT environment variable
# 2. Add health check support
# 3. Configure for Railway's proxy

COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration
ENV CATALINA_OPTS="-Dorg.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar \
                   -Dorg.apache.catalina.startup.TldConfig.jarsToSkip=*.jar \
                   -Dserver.port=$PORT"

# Railway automatically sets $PORT (usually 8080, but don't hardcode)
EXPOSE $PORT

# Modified CMD for Railway:
CMD sed -i "s/port=\"8080\"/port=\"$PORT\"/" /usr/local/tomcat/conf/server.xml && \
    catalina.sh run