package com.sebczu.poc.r2dbc;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("player")
public class Player {

    @Id
    Integer id;
    String name;

    public Player(String name) {
        this.name = name;
    }
}
