FROM openjdk:17-oracle
COPY target/posts-api-0.0.1-SNAPSHOT.jar /posts-api-0.0.1.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/posts-api-0.0.1.jar"]