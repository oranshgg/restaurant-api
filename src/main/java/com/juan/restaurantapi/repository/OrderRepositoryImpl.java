package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJPARepository orderJPARepository;

    @Override
    public Optional<OrderEntity> getOrderById(Long orderId) {
        return orderJPARepository.findById(orderId);
    }

    @Override
    public Optional<OrderEntity> createOrder(OrderEntity orderEntity) {
        return Optional.of(orderJPARepository.save(orderEntity));
    }
}
