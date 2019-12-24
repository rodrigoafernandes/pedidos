FROM fabric8/java-alpine-openjdk8-jre
COPY target/*.jar /deployments/app.jar
ENV CONSUL_HOST=${CONSUL_HOST}
ENV VAULT_SCHEME=${VAULT_SCHEME}
ENV VAULT_HOST=${VAULT_HOST}
ENV VAULT_SECURITY_TOKEN=${VAULT_SECURITY_TOKEN}
ENV JAVA_OPTIONS="-Dspring.profiles.active=prod"
ENTRYPOINT [ "/deployments/run-java.sh" ]