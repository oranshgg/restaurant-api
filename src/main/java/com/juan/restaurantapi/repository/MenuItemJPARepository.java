package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemJPARepository extends JpaRepository<MenuItemEntity, Long> {
}
