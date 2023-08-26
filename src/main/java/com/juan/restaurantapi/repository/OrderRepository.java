package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<List<OrderDTO>> getAllOrders();

    Optional<OrderEntity> createOrder(OrderEntity orderEntity);
}
