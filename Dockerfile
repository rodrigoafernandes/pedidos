FROM fabric8/java-alpine-openjdk8-jre
COPY target/*.jar /deployments/app.jar
ENV JAVA_OPTIONS="-Dspring.profiles.active=prod"
ENTRYPOINT [ "/deployments/run-java.sh" ]