package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantJPARepository extends JpaRepository <RestaurantEntity, Long> {
}
