package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

class ListUserControllerTest extends UserControllerTest {

    @Test
    void shouldFetchAllUsers() {
        List<UserEntity> users = List.of(new UserEntity("John"), new UserEntity("John2"));
        insertUsers(users).blockLast();

        webClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(user -> user.size(), equalTo(2))
                .value(user -> user.get(0).getName(), equalTo("John"))
                .value(user -> user.get(1).getName(), equalTo("John2"));
    }

}
