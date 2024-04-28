package com.juan.restaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProfitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitApplication.class, args);
    }

}
