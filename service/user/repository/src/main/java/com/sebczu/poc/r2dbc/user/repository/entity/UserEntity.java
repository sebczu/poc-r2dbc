package com.sebczu.poc.r2dbc.user.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    Integer id;
    String name;

    public UserEntity(String name) {
        this.name = name;
    }
}
