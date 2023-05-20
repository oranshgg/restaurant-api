package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.mapper.OrderMapper;
import com.juan.restaurantapi.repository.OrderRepository;
import com.juan.restaurantapi.repository.RestaurantJPARepository;
import com.juan.restaurantapi.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Optional<List<OrderDTO>> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO) {
        Optional<RestaurantEntity> restaurantEntity =
                restaurantRepository.getRestaurantById(createOrderDTO.getRestaurant_id());

        if (restaurantEntity.isEmpty()) {
            throw new BadRequestException(
                String.format("No existe ningún restaurante con el id %d", createOrderDTO.getRestaurant_id())
            );
        }

        OrderEntity orderEntity = OrderMapper.mapRequestToEntity(createOrderDTO, restaurantEntity.get());
        Optional<OrderDTO> createdOrder = orderRepository.createOrder(orderEntity);

        if(createdOrder.isEmpty()) {
            return Optional.empty();
        }

        return createdOrder;
    }
}
