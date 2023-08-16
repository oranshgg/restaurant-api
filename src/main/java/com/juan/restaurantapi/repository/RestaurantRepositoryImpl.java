package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.RestaurantEntity;
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

    @Override
    public Optional<RestaurantDTO> getRestaurantById(Long restaurantId) {
        Optional<RestaurantEntity> restaurantEntity = restaurantJPARepository.findById(restaurantId);

        if (restaurantEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(RestaurantMapper.mapEntityToDTO(restaurantEntity.get()));
    }
}
