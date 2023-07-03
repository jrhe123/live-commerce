FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY /arthas-bin.zip /opts/arthas-bin.zip
ADD qiyu-live-api-docker.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]