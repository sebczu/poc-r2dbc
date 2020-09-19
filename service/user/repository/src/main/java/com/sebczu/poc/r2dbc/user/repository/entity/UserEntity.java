package com.sebczu.poc.r2dbc.user.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
@ToString
public class UserEntity {

    @Id
    private Integer id;
    private String name;

    public UserEntity(String name) {
        this.name = name;
    }
}
