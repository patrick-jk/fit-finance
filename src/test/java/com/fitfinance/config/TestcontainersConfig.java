package com.fitfinance.config;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    MySQLContainer<?> mySqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:9.0.1"))
                .withDatabaseName("fit_finance");
    }
}
