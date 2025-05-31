# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM tomcat:10.1.24-jdk17-temurin
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*

# Copy WAR and ensure proper permissions
COPY --from=build /app/target/voteSphere.war webapps/ROOT.war
RUN chmod -R 755 webapps/

# Create entrypoint script
RUN echo $'#!/bin/sh\n\
sed -i "s/port=\"8080\"/port=\"$PORT\"/" conf/server.xml\n\
catalina.sh run\n' > entrypoint.sh && \
    chmod +x entrypoint.sh

# Environment configuration
ENV CATALINA_OPTS="-Dorg.apache.catalina.startup.ContextConfig.jarsToSkip=*.jar \
                   -Dorg.apache.catalina.startup.TldConfig.jarsToSkip=*.jar \
                   -Dserver.port=\$PORT"
EXPOSE $PORT
RUN echo $'#!/bin/sh\n\
sed -i "s/port=\"8080\"/port=\"$PORT\"/" conf/server.xml\n\
catalina.sh run\n' > entrypoint.sh && \
    chmod +x entrypoint.sh
RUN printf '#!/bin/sh\nsed -i "s/port=\\"8080\\"/port=\\"$PORT\\"/" conf/server.xml\nexec catalina.sh run\n' > entrypoint.sh && \
    chmod +x entrypoint.sh
