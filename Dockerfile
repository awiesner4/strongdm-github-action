FROM openjdk:11
WORKDIR /
COPY strongdm-github-action-0.0.1-SNAPSHOT-jar-with-dependencies.jar /github/workspace/strongdm-github-action-0.0.1-SNAPSHOT.jar
EXPOSE 18080
CMD pwd && ls -la