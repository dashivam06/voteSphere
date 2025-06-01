# Runtime stage
FROM tomcat:10.1.24-jdk17-temurin

# Remove default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy WAR file (Tomcat will auto-extract it when starting)
COPY --from=build /app/target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Environment configuration
ENV CATALINA_OPTS="-Dserver.port=${PORT:-8080} -Dspring.config.location=file:/usr/local/tomcat/conf/"
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Copy external configuration
COPY src/main/resources/application.properties /usr/local/tomcat/conf/

# Expose port (informational only - Railway will use its own port mapping)
EXPOSE ${PORT:-8080}

# Healthcheck (modified for Railway)
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

CMD ["catalina.sh", "run"]