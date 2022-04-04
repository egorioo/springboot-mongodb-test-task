FROM openjdk:11
ADD /target/Test-Task-0.0.1-SNAPSHOT.war application.war
COPY src/main/resources src/main/resources
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.war"]