package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemJPARepository extends JpaRepository<MenuItemEntity, Long> {

    @Query(value = "SELECT * FROM menu_items WHERE restaurant_id = :id", nativeQuery = true)
    List<MenuItemEntity> findMenuItemEntitiesByRestaurantId(@Param("id") Long id);
}
