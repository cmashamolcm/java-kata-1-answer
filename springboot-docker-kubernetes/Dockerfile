FROM openjdk:16
ADD target/maven-docker-jenkins-app.jar maven-docker-jenkins-app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "maven-docker-jenkins-app.jar"]