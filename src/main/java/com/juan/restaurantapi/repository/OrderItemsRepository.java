package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;

import java.util.List;

public interface OrderItemsRepository {

    void saveMenuItems(OrderEntity orderEntity, List<MenuItemEntity> menuItemEntities);
}
