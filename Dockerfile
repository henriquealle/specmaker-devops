FROM openjdk:17-oracle
ADD target/specmaker-0.0.1-SNAPSHOT.jar /opt/specmaker-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT java -Duser.timezone=America/Sao_Paulo $JAVA_OPTIONS -jar /opt/specmaker-0.0.1-SNAPSHOT.jar
