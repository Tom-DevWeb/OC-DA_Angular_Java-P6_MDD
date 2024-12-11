package com.mdd.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackMddApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackMddApplication.class, args);
    }

}
