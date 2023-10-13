package com.example.ch05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication


public class Ch05Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch05Application.class, args);
    }

}
