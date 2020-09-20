package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.api.UserAPI;
import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.UserRepository;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserRepository repository;

    public Flux<User> list() {
        log.info("list users");
        return repository.findAll()
                .delayElements(Duration.ofMillis(100))
                .map(entity -> new User(entity.getId(), entity.getName()))
                .log();
    }

    public Mono<User> get(Integer id) {
        log.info("get user {}", id);
        return repository.findById(id)
                .map(entity -> new User(entity.getId(), entity.getName()))
                .log();
    }

    public Mono<User> create(User user) {
        log.info("create user {}", user);
        return Mono.just(user)
                .map(user1 -> new UserEntity(user1.getName()))
                .flatMap(repository::save)
                .map(entity -> new User(entity.getId(), entity.getName()))
                .log();
    }

    public Mono<User> update(Integer id, User user) {
        log.info("update user {} ({})", id, user);
        return Mono.just(user)
                .map(user1 -> new UserEntity(id, user1.getName()))
                .flatMap(repository::save)
                .map(entity -> new User(id, entity.getName()))
                .log();
    }

    public Mono<Void> delete(Integer id) {
        log.info("delete user {}", id);
        return repository.deleteById(id)
                .log();
    }
}
