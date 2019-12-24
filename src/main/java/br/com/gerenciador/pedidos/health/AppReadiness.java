package br.com.gerenciador.pedidos.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Readiness
@ApplicationScoped
public class AppReadiness implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder health = HealthCheckResponse.named("Health check com o Database");
        if (entityManager.isOpen()) {
            health.up();
        } else {
            health.down();
        }
        return health.build();
    }

}
