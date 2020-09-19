package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

class GetUserControllerTest extends UserControllerTest {

    @Test
    void shouldFetchSingleUser() {
        List<UserEntity> users = List.of(new UserEntity("John"),
                new UserEntity("John2"),
                new UserEntity("JohnLast"));
        UserEntity user = insertUsers(users).blockLast();

        webClient.get()
                .uri(URL + "/{id}", user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(u -> u.getId(), equalTo(user.getId()))
                .value(u -> u.getName(), equalTo(user.getName()));
    }

}
