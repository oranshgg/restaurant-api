package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService{

    private final MenuItemRepository menuItemRepository;
    @Override
    public Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.getMenuItemsByRestaurantId(restaurantId);
    }
}
