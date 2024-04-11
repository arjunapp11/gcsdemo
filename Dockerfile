FROM openjdk:17
WORKDIR /app
COPY target/spring-boot-gcp-0.0.1-SNAPSHOT.jar spring-boot-gcp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "spring-boot-gcp-0.0.1-SNAPSHOT.jar"]
