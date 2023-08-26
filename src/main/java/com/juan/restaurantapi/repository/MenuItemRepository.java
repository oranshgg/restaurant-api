package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {

    Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId);

    Optional<MenuItemEntity> getMenuItemById(Long menuItemId);
}
