package br.com.gerenciador.pedidos.config;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile(value = {"ti", "test-db"})
public class MariaDBTestConfig {

    @Value("${gerenciador.pedidos.database.name}")
    private String databaseName;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Bean
    public MariaDB4jSpringService mariaDB4jSpringService() {
        return new MariaDB4jSpringService();
    }

    @Bean
    public DataSource dataSource() throws ManagedProcessException {
        mariaDB4jSpringService().getDB().createDB(databaseName);

        DBConfigurationBuilder config = mariaDB4jSpringService().getConfiguration();

        return DataSourceBuilder.create().username(datasourceUsername).password(datasourcePassword)
                .url(config.getURL(databaseName)).driverClassName(datasourceDriver).build();
    }

}
