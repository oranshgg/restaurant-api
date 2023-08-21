package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;

public class MenuItemMapper {

    private MenuItemMapper() {};

    public static MenuItemDTO mapEntityToDTO(MenuItemEntity menuItemEntity) {
        return MenuItemDTO.builder()
                .id(menuItemEntity.getId())
                .name(menuItemEntity.getName())
                .price(menuItemEntity.getPrice())
                .description(menuItemEntity.getDescription())
                .extraFields(menuItemEntity.getExtraFields())
                .available(menuItemEntity.getAvailable())
                .restaurant(RestaurantMapper.mapEntityToDTO(menuItemEntity.getRestaurant()))
                .build();
    }
}
