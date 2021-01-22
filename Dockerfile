FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/football-league-module.jar

WORKDIR /opt/app

COPY ${JAR_FILE} football-league-module.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","football-league-module.jar"]
