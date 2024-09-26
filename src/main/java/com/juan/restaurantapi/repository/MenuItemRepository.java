package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.CreateMenuItemDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {

    Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId);

    Optional<List<MenuItemDTO>> getMenuItemsByRestaurantIdNoRestaurantNode(Long restaurantId);

    Optional<MenuItemEntity> getMenuItemById(Long menuItemId);

    Optional<Integer> createMenuItem(CreateMenuItemDTO createMenuItemDTO);

    Optional<MenuItemEntity> updateMenuItem(MenuItemEntity menuItemEntityUpdated);
}
