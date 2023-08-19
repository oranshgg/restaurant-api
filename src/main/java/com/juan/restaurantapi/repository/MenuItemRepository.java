package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;

import java.util.List;

public interface MenuItemRepository {

    List<MenuItemEntity> getMenuItemsByRestaurantId(Long restaurantId);
}
