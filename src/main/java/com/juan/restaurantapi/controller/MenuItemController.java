package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.CreateMenuItemDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cors.port}")
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

    @PostMapping("/menu-items/create")
    public ResponseEntity<?> createMenuItem(@RequestBody CreateMenuItemDTO createMenuItemDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<CreateMenuItemDTO> createdItem = menuItemService.createMenuItem(createMenuItemDTO);
            if (createdItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al crear el producto.");
            }
            response.put("message", String.format("El producto %s fue creado con éxito", createdItem.get().getName()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
