package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/ping")
    public String ping() {
        return restaurantService.ping();
    }
}
