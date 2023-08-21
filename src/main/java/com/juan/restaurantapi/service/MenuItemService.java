package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuItemService {

    Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId);
}
