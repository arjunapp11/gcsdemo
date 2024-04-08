FROM openjdk:17
WORKDIR /app
COPY target/spring-boot-gcp-0.0.1-SNAPSHOT.jar spring-boot-gcp-0.0.1-SNAPSHOT.jar
COPY src/main/resources/my-project-file-410710-f6557f4b5872.json /app/my-project-file-410710-f6557f4b5872.json
ENTRYPOINT ["java", "-jar", "spring-boot-gcp-0.0.1-SNAPSHOT.jar"]