package com.juan.restaurantapi.service;

import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Override
    public String ping() {
        return "Pong";
    }
}
