package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;

public interface OrderItemsRepository {

    void saveMenuItems(OrderEntity orderEntity, List<MenuItemEntity> menuItemEntities);

    Optional<List<OrderItemEntity>> getMenuItemsByOrderId(Long orderId);
}
