# Build stage
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM tomcat:10.1.24-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy application and resources
COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=build /app/src/main/resources/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Environment configuration
ENV CATALINA_OPTS="-Dorg.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar \
                   -Dorg.apache.catalina.startup.TldConfig.jarsToSkip=*.jar \
                   -Dserver.port=\$PORT \
                   -Dspring.config.additional-location=file:/usr/local/tomcat/webapps/ROOT/WEB-INF/classes/"

EXPOSE 8080
CMD sed -i "s/port=\"8080\"/port=\"\$PORT\"/" /usr/local/tomcat/conf/server.xml && \
    catalina.sh run