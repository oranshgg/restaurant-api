package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.mapper.OrderMapper;
import com.juan.restaurantapi.mapper.RestaurantMapper;
import com.juan.restaurantapi.repository.MenuItemRepository;
import com.juan.restaurantapi.repository.OrderItemsRepository;
import com.juan.restaurantapi.repository.OrderRepository;
import com.juan.restaurantapi.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemsRepository orderItemsRepository;

    @Override
    public Optional<List<OrderDTO>> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO) {
        Optional<RestaurantDTO> restaurantDTO = restaurantRepository.getRestaurantById(createOrderDTO.getRestaurantId());
        if (restaurantDTO.isEmpty()) {
            throw new BadRequestException(
                String.format("No existe ningún restaurante con el id %d", createOrderDTO.getRestaurantId())
            );
        }

        Optional<List<MenuItemDTO>> menuList = menuItemRepository.getMenuItemsByRestaurantId(createOrderDTO.getRestaurantId());
        if (menuList.orElse(new ArrayList<>()).isEmpty()) {
            throw new BadRequestException(
                    String.format("No existen items para el restaurante con el id %d", createOrderDTO.getRestaurantId())
            );
        }

        //TODO: validar que el array de menu items pertenezcan al restaurante

        List<MenuItemEntity> menuItemEntities = this.getMenuItemEntities(createOrderDTO.getMenuItems());

        RestaurantEntity restaurantEntity = RestaurantMapper.mapDTOToEntity(restaurantDTO.get());
        OrderEntity orderEntity = OrderMapper.mapRequestToEntity(restaurantEntity);

        Optional<OrderEntity> createdOrder = orderRepository.createOrder(orderEntity);

        if (createdOrder.isEmpty()) {
            return Optional.empty();
        }

        //guardar la relación order - menu item
        orderItemsRepository.saveMenuItems(createdOrder.get(), menuItemEntities);

        return Optional.of(OrderMapper.mapEntityToDTO(createdOrder.get()));
    }

    private List<MenuItemEntity> getMenuItemEntities(List<Long> menuItemsIds) {
        List<MenuItemEntity> menuItemEntities = new ArrayList<>();
        for (Long id : menuItemsIds) {
            Optional<MenuItemEntity> menuItemEntity = menuItemRepository.getMenuItemById(id);
            menuItemEntity.ifPresent(menuItemEntities::add);
        }
        return menuItemEntities;
    }
}
