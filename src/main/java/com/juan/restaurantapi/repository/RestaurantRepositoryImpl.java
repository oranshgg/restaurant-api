package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJPARepository restaurantJPARepository;

    @Override
    public Optional<List<RestaurantDTO>> getAllRestaurants() {
        return Optional.of(
                restaurantJPARepository.findAll()
                .stream()
                .map(RestaurantMapper::mapEntityToDTO)
                .toList());
    }
}
