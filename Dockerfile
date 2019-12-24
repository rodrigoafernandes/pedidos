FROM fabric8/java-alpine-openjdk8-jre
ENV AB_ENABLED=jmx_exporter
COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV CONSUL_HOST=${CONSUL_HOST}
ENV VAULT_SECURITY_TOKEN=${VAULT_SECURITY_TOKEN}
ENTRYPOINT [ "/deployments/run-java.sh" ]