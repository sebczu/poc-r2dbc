package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;

class ListUserControllerTest extends UserControllerTest {

    private final static Function<List<User>, Iterable<? extends String>> NAME_EXTRACT = users -> users.stream()
            .map(User::getName)
            .collect(Collectors.toList());

    @Test
    void shouldFetchAllUsers() {
        List<UserEntity> users = List.of(new UserEntity("John"), new UserEntity("John2"));
        insertUsers(users).blockLast();

        webClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .value(NAME_EXTRACT, containsInAnyOrder("John", "John2"));
    }

}
