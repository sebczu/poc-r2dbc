package com.sebczu.poc.r2dbc.user.api;

import com.sebczu.poc.r2dbc.user.domain.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping(produces = APPLICATION_STREAM_JSON_VALUE)
    Flux<User> list();

    @GetMapping(value = "/{id}", produces = APPLICATION_STREAM_JSON_VALUE)
    Mono<User> getById(@PathVariable("id") Integer id);

    @PostMapping(consumes = APPLICATION_STREAM_JSON_VALUE, produces = APPLICATION_STREAM_JSON_VALUE)
    Mono<User> save(@RequestBody User user);

    @PutMapping(value = "/{id}", consumes = APPLICATION_STREAM_JSON_VALUE, produces = APPLICATION_STREAM_JSON_VALUE)
    Mono<User> update(@PathVariable("id") Integer id, @RequestBody User user);

    @DeleteMapping(value = "/{id}", produces = APPLICATION_STREAM_JSON_VALUE)
    Mono<Void> delete(@PathVariable Integer id);
}
