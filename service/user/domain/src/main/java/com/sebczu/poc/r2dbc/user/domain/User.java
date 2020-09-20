package com.sebczu.poc.r2dbc.user.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Integer id;
    private String name;

    public User(String name) {
        this.name = name;
    }

}
