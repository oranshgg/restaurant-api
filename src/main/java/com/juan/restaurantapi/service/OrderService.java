package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    /**
     * get order by id
     */
    Optional<OrderDTO> getOrderById(Long orderId);

    /**
     * get last orders from previous 6 hours
     */
    Optional<List<OrderDTO>> getLastOrders(Long restaurantId);
    /**
     * create order
     */
    Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO);
}
