# Use official Tomcat image
FROM tomcat:10.1.24-jdk17-temurin

# Remove default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy your WAR file directly (replace with your actual WAR file path)
COPY target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration for Railway
ENV CATALINA_OPTS="-Dserver.port=${PORT:-8080} -Dspring.config.location=file:/usr/local/tomcat/conf/"
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Copy external configuration if needed
COPY src/main/resources/application.properties /usr/local/tomcat/conf/

# Informational port exposure (Railway will handle actual port mapping)
EXPOSE ${PORT:-8080}

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:${PORT:-8080}/ || exit 1

CMD ["catalina.sh", "run"]