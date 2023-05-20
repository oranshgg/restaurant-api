package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.RestaurantEntity;
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
    public Optional<RestaurantEntity> getRestaurantById(Long restaurantId) {
        return restaurantRepository.getRestaurantById(restaurantId);
    }
}
