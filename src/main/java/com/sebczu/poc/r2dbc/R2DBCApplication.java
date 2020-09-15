package com.sebczu.poc.r2dbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class R2DBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2DBCApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository repository) {
        return (args) -> {

            System.out.println("create table");

            // save a few customers
            repository.saveAll(Arrays.asList(new Player("Jack1"),
                    new Player("Jack2"),
                    new Player("Jack3")))
                    .blockLast(Duration.ofSeconds(10));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().doOnNext(customer -> {
                log.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));

            log.info("");

            // fetch an individual customer by ID
            repository.findById(1).doOnNext(customer -> {
                log.info("Customer found with findById(1L):");
                log.info("--------------------------------");
                log.info(customer.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));


            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
//            repository.findByLastName("Bauer").doOnNext(bauer -> {
//                log.info(bauer.toString());
//            }).blockLast(Duration.ofSeconds(10));;
            log.info("");
        };
    }

}
