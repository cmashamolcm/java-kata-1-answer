FROM openjdk:16
ADD target/maven-docker-jenkins-app.jar maven-docker-jenkins-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "maven-docker-jenkins-app.jar"]
