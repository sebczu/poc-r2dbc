package com.sebczu.poc.r2dbc.user.controller;

import com.sebczu.poc.r2dbc.user.repository.UserRepository;
import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping(produces = APPLICATION_STREAM_JSON_VALUE)
    public Flux<UserEntity> list() {
        return repository.findAll()
                .delayElements(Duration.ofMillis(100));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_STREAM_JSON_VALUE)
    public Mono<UserEntity> getById(@PathVariable("id") Integer id) {
        return repository.findById(id);
    }

    @PostMapping(consumes = APPLICATION_STREAM_JSON_VALUE, produces = APPLICATION_STREAM_JSON_VALUE)
    public Mono<UserEntity> save(@RequestBody UserEntity user) {
        return repository.save(user);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_STREAM_JSON_VALUE, produces = APPLICATION_STREAM_JSON_VALUE)
    public Mono<UserEntity> save(@PathVariable("id") Integer id, @RequestBody UserEntity user) {
        user.setId(id);
        return repository.save(user);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> delete(@PathVariable Integer id) {
        return repository.deleteById(id);
    }
}
