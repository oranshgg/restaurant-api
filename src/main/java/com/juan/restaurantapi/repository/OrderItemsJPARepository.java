package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsJPARepository extends JpaRepository<OrderItemEntity, Long> {
    @Query(value = "SELECT * FROM order_items WHERE order_id = :id", nativeQuery = true)
    List<OrderItemEntity> findOrderItemsByOrderId(@Param("id") Long id);

    @Query(value = "SELECT * FROM order_items WHERE order_id IN (:orderIds)", nativeQuery = true)
    List<OrderItemEntity> findOrderItemsByOrderIds(@Param("orderIds") List<Long> orderIds);
}
