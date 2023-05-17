package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.entity.OrderEntity;

public final class OrderMapper {

    private OrderMapper() {};

    public static OrderDTO mapEntityToDTO(OrderEntity order) {
        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .restaurant(RestaurantMapper.mapEntityToDTO(order.getRestaurant()))
                .build();
    }
}
