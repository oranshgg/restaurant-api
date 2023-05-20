package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.RestaurantEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    /**
     * get all restaurants
     * @return
     */
    Optional<List<RestaurantDTO>> getAllRestaurants();

    /**
     *
     */
    Optional<RestaurantEntity> getRestaurantById(Long restaurantId);
}
