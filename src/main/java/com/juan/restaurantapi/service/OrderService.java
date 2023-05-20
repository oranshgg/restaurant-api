package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    /**
     * get all orders
     */
    Optional<List<OrderDTO>> getAllOrders();

    /**
     * create order
     */
    Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO);
}
