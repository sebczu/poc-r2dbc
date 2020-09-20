package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.repository.UserRepository;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
@ContextConfiguration(initializers = {UserControllerTest.ContextInitializer.class})
@AutoConfigureWebTestClient
@EnableR2dbcRepositories(basePackages = "com.sebczu")
abstract class UserControllerTest {

    protected String URL = "/users";

    @Autowired
    protected WebTestClient webClient;

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected DatabaseClient databaseClient;

    @BeforeEach
    @AfterEach
    private void cleanRepository() {
        databaseClient.delete().from(UserEntity.class)
                .fetch()
                .rowsUpdated()
                .block();
    }

    protected Flux<UserEntity> insertUsers(List<UserEntity> users) {
        return databaseClient.insert().into(UserEntity.class)
                .using(Flux.fromStream(users.stream()))
                .fetch().all()
                .map(user -> new UserEntity( (Integer) user.get("id"), (String) user.get("name")));
    }

    protected Flux<UserEntity> getAllUsers() {
        return databaseClient.select().from(UserEntity.class)
                .fetch().all();
    }

    static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
                    .withDatabaseName("test")
                    .withUsername("admin")
                    .withPassword("test");

            postgreSQLContainer.start();

            TestPropertyValues.of(
                    "spring.r2dbc.url=" + "r2dbc:postgresql://" + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort() + "/" + postgreSQLContainer.getDatabaseName(),
                    "spring.r2dbc.username=" + postgreSQLContainer.getUsername(),
                    "spring.r2dbc.password=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext);
        }
    }

    @TestConfiguration
    static class TestInitalizer {
        @Bean
        public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            initializer.setDatabasePopulator(populator);

            return initializer;
        }
    }
}
