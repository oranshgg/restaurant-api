package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cors.port}")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/ping")
    public String ping() {
        return restaurantService.ping();
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        Optional<List<RestaurantDTO>> allRestaurants = restaurantService.getAllRestaurants();

        if (allRestaurants.isEmpty() || allRestaurants.get().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(allRestaurants.get());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long restaurantId) {
        try {
            Optional<RestaurantDTO> restaurant = restaurantService.getRestaurantById(restaurantId);
            if (restaurant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(restaurant.get());
        } catch (BadRequestException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
