package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.RestaurantEntity;

public final class RestaurantMapper {

    private RestaurantMapper() {}

    public static RestaurantDTO mapEntityToDTO(RestaurantEntity restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getName())
                .build();
    }
}
