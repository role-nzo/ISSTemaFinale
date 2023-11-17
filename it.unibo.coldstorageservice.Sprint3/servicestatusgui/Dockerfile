FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8081
COPY build/libs/servicestatusgui-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]