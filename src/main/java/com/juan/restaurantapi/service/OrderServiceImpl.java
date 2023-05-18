package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Optional<List<OrderDTO>> getAllOrders() {
        return orderRepository.getAllOrders();
    }
}
