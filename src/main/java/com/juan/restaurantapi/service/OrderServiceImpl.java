package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.mapper.OrderMapper;
import com.juan.restaurantapi.mapper.RestaurantMapper;
import com.juan.restaurantapi.repository.MenuItemRepository;
import com.juan.restaurantapi.repository.OrderRepository;
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
    private final MenuItemRepository menuItemRepository;

    @Override
    public Optional<List<OrderDTO>> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO) {
        Optional<RestaurantDTO> restaurantDTO =
                restaurantRepository.getRestaurantById(createOrderDTO.getRestaurant_id());

        if (restaurantDTO.isEmpty()) {
            throw new BadRequestException(
                String.format("No existe ningún restaurante con el id %d", createOrderDTO.getRestaurant_id())
            );
        }

        RestaurantEntity restaurantEntity = this.mapRestaurantDTOToEntity(restaurantDTO.get());

        List<MenuItemEntity> menuList = menuItemRepository.getMenuItemsByRestaurantId(createOrderDTO.getRestaurant_id());

        OrderEntity orderEntity = this.mapCreateOrderDTOToEntity(restaurantEntity);
        Optional<OrderDTO> createdOrder = orderRepository.createOrder(orderEntity);

        if(createdOrder.isEmpty()) {
            return Optional.empty();
        }

        return createdOrder;
    }

    private RestaurantEntity mapRestaurantDTOToEntity(RestaurantDTO restaurantDTO) {
        return RestaurantMapper.mapDTOToEntity(restaurantDTO);
    }

    private OrderEntity mapCreateOrderDTOToEntity(RestaurantEntity restaurantEntity) {
        return OrderMapper.mapRequestToEntity(restaurantEntity);
    }
}
