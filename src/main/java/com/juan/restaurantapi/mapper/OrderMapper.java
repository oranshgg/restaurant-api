package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;

public final class OrderMapper {

    private OrderMapper() {};

    public static OrderDTO mapEntityToDTO(OrderEntity order) {
        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .restaurant(RestaurantMapper.mapEntityToDTO(order.getRestaurant()))
                .extraFields(order.getExtraFields())
                .build();
    }

    public static OrderEntity mapRequestToEntity(RestaurantEntity restaurantEntity, CreateOrderDTO createOrderDTO) {
        return OrderEntity.builder()
                .restaurant(restaurantEntity)
                .extraFields(createOrderDTO.getExtraFields())
                .build();
    }
}
