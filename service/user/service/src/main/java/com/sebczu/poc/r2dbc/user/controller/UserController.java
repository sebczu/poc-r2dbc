package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.api.UserAPI;
import com.sebczu.poc.r2dbc.user.domain.User;
import com.sebczu.poc.r2dbc.user.repository.UserRepository;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserRepository repository;

    public Flux<User> list() {
        return repository.findAll()
                .delayElements(Duration.ofMillis(100))
                .map(entity -> new User(entity.getId(), entity.getName()));
    }

    public Mono<User> getById(Integer id) {
        return repository.findById(id)
                .map(entity -> new User(entity.getId(), entity.getName()));
    }

    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(user1 -> new UserEntity(user1.getName()))
                .flatMap(repository::save)
                .map(entity -> new User(entity.getId(), entity.getName()));
    }

    public Mono<User> update(Integer id, User user) {
        return Mono.just(user)
                .map(user1 -> new UserEntity(id, user1.getName()))
                .flatMap(repository::save)
                .map(entity -> new User(id, entity.getName()));
    }

    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }
}
