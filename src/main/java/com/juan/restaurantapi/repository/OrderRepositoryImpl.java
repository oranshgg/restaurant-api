package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJPARepository orderJPARepository;

    @Override
    public Optional<List<OrderDTO>> getAllOrders() {
        return Optional.of(
                orderJPARepository.findAll()
                        .stream()
                        .map(OrderMapper::mapEntityToDTO)
                        .toList());
    }
}
