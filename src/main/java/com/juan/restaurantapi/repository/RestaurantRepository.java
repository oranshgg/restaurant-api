package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.RestaurantDTO;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    Optional<List<RestaurantDTO>> getAllRestaurants();
}
