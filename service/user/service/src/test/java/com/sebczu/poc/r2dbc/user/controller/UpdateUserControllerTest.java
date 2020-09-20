package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

class UpdateUserControllerTest extends UserControllerTest {

    @Test
    void shouldUpdateSingleUser() {
        List<UserEntity> users = List.of(new UserEntity("John"),
                new UserEntity("John2"),
                new UserEntity("John3"));
        UserEntity entity = insertUsers(users).blockLast();
        User user = new User("update");

        webClient.put()
                .uri(URL + "/{id}", entity.getId())
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").value(equalTo(user.getName()));

        StepVerifier
                .create(getAllUsers())
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

}
