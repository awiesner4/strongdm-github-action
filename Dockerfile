FROM openjdk:11
WORKDIR /
ADD strongdm-github-action-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/bin/strongdm-github-action-0.0.1-SNAPSHOT.jar
EXPOSE 18080
CMD java -jar /usr/bin/strongdm-github-action-0.0.1-SNAPSHOT.jar