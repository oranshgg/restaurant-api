package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<List<OrderDTO>> getAllOrders();
}
