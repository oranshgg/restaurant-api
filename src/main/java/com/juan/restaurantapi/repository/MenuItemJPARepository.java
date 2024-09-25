package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemJPARepository extends JpaRepository<MenuItemEntity, Long> {

    @Query(value = "SELECT * FROM menu_items WHERE restaurant_id = :id", nativeQuery = true)
    List<MenuItemEntity> findMenuItemEntitiesByRestaurantId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO menu_items (name, price, description, available, restaurant_id, subgroup) " +
            "VALUES (:name, :price, :description, :available, :restaurantId, :subgroup)", nativeQuery = true)
    Integer createMenuItem(
            @Param("name") String name,
            @Param("price") BigDecimal price,
            @Param("description") String description,
            @Param("available") Boolean available,
            @Param("restaurantId") Long restaurantId,
            @Param("subgroup") String subgroup
    );
}
