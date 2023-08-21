package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;
    @GetMapping("/menu-items/restaurant/{restaurantId}")
    public ResponseEntity<?> getMenuItemsByRestaurantId(@PathVariable Long restaurantId) {
        Optional<List<MenuItemDTO>> menuItems = menuItemService.getMenuItemsByRestaurantId(restaurantId);

        if (menuItems.orElse(new ArrayList<>()).isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", String.format("No existen items para el restaurante con el id %d", restaurantId));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(menuItems.get());
    }
}
