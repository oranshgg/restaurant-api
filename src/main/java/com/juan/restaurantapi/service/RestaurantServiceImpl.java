package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    @Override
    public String ping() {
        return "Pong";
    }

    @Override
    public Optional<List<RestaurantDTO>> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override
    public Optional<RestaurantDTO> getRestaurantById(Long restaurantId) {
        if (restaurantId < 1) {
            throw new BadRequestException(
                String.format("El restaurant id es inválido")
            );
        }
        return restaurantRepository.getRestaurantById(restaurantId);
    }
}
