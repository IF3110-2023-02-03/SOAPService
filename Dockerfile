FROM amazoncorretto:8

WORKDIR /app

COPY target/soap-service-1.0-SNAPSHOT-jar-with-dependencies.jar /app/soap-service-1.0-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "soap-service-1.0-SNAPSHOT-jar-with-dependencies.jar"]