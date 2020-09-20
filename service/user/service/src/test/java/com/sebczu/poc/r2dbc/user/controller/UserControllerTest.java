package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.repository.UserRepository;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@SpringBootTest
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
    public void cleanRepository() {
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
}
