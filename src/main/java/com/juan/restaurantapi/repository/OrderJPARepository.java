package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJPARepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT * FROM orders WHERE restaurant_id = :restaurantId and orders.date > DATE_SUB(NOW(), INTERVAL 6 HOUR)", nativeQuery = true)
    Optional<List<OrderEntity>> getLastOrders(@Param("restaurantId") Long restaurantId);
}
