# Use official Tomcat image with Java 17
FROM tomcat:10.1-jdk17

# Clean default apps and deploy your WAR
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/voteSphere.war /usr/local/tomcat/webapps/ROOT.war

# Expose port (Railway will override $PORT)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]