# 🏗️ Build stage using Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 🚀 Runtime stage using Tomcat
FROM tomcat:10.1.24-jdk17-temurin
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*

# ✅ Copy WAR to Tomcat's ROOT.war (Railway serves from /)
COPY --from=build /app/target/voteSphere.war webapps/ROOT.war

RUN cd webapps && jar xf ROOT.war

# 🐛 Debug info - Print working directory and contents
RUN echo "🧭 Current directory:" && pwd && \
    echo "📂 Contents:" && ls -la && \
    echo "📂 webapps contents:" && ls -la webapps/

# 🛠️ Set environment variable for Railway
ENV CATALINA_OPTS="-Dserver.port=${PORT}"

# ❤️ Health check to verify app is alive
HEALTHCHECK --interval=30s --timeout=5s \
  CMD curl -f http://localhost:${PORT}/ || exit 1

# 🔓 Expose Railway's provided port
EXPOSE ${PORT}

# 🚀 Start Tomcat
CMD ["catalina.sh", "run"]
