package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.domain.User;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.hamcrest.Matchers.equalTo;

class CreateUserControllerTest extends UserControllerTest {

    @Test
    void shouldCreateSingleUser() {
        User user = new User("John");

        webClient.post()
                .uri(URL)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").value(equalTo(user.getName()));

        StepVerifier
                .create(getAllUsers())
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

}
