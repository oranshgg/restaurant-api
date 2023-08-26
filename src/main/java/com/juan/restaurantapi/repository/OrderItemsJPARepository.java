package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsJPARepository extends JpaRepository<OrderItemEntity, Long> {
}
