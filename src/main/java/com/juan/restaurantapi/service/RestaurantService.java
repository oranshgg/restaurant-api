package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.RestaurantEntity;
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

    /**
     * get restaurant by id
     */
    Optional<RestaurantEntity> getRestaurantById(Long restaurantId);
}
