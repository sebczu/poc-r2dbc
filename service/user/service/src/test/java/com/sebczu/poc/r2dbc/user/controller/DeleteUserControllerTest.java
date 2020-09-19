package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class DeleteUserControllerTest extends UserControllerTest {

    @Test
    void shouldDeleteSingleUser() {
        List<UserEntity> users = List.of(new UserEntity("John"),
                new UserEntity("John2"),
                new UserEntity("John3"));
        UserEntity user = insertUsers(users).blockLast();

        webClient.delete()
                .uri(URL + "/{id}", user.getId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        StepVerifier
                .create(getAllUsers())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

}
