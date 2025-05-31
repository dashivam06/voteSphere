FROM tomcat:10.1.24-jdk17-temurin
WORKDIR /usr/local/tomcat
RUN rm -rf webapps/*

COPY --from=build /app/target/voteSphere.war webapps/ROOT.war

RUN mkdir -p webapps/ROOT && cd webapps/ROOT && jar xf ../ROOT.war

RUN echo "🧭 Current directory:" && pwd && \
    echo "📂 Contents:" && ls -la && \
    echo "📂 webapps contents:" && ls -la webapps/ && \
    echo "📂 webapps/ROOT contents:" && ls -la webapps/ROOT/

ENV CATALINA_OPTS="-Dserver.port=${PORT}"

HEALTHCHECK --interval=30s --timeout=5s \
  CMD curl -f http://localhost:${PORT}/ || exit 1

EXPOSE ${PORT}

CMD ["catalina.sh", "run"]
