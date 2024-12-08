package com.mdd.back;

import org.springframework.boot.SpringApplication;

public class TestBackMddApplication {

    public static void main(String[] args) {
        SpringApplication.from(BackMddApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
