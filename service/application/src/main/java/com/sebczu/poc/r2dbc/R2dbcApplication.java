package com.sebczu.poc.r2dbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class R2dbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcApplication.class, args);
    }

}
