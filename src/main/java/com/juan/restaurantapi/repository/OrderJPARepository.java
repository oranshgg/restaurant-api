package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJPARepository extends JpaRepository<OrderEntity, Long> {
}
