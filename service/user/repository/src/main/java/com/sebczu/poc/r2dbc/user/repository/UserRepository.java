package com.sebczu.poc.r2dbc.user.repository;

import com.sebczu.poc.r2dbc.user.repository.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {
}
