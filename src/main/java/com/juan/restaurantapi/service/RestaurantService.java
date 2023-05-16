package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RestaurantService {

    /**
     *  ping endpoint
     * @return
     */
    String ping();

    /**
     * get all restaurants
     */
    Optional<List<RestaurantDTO>> getAllRestaurants();
}
