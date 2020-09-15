package com.sebczu.poc.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {
}
