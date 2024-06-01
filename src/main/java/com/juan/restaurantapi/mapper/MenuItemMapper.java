package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;

public class MenuItemMapper {

    private MenuItemMapper() {
    }

    public static MenuItemDTO mapEntityToDTO(MenuItemEntity menuItemEntity) {
        return MenuItemDTO.builder()
                .id(menuItemEntity.getId())
                .name(menuItemEntity.getName())
                .price(menuItemEntity.getPrice())
                .description(menuItemEntity.getDescription())
                .available(menuItemEntity.getAvailable())
                .restaurant(RestaurantMapper.mapEntityToDTO(menuItemEntity.getRestaurant()))
                .subgroup(menuItemEntity.getSubgroup())
                .build();
    }

    public static MenuItemDTO mapEntityToDTOWithoutRestaurant(MenuItemEntity menuItemEntity) {
        return MenuItemDTO.builder()
                .id(menuItemEntity.getId())
                .name(menuItemEntity.getName())
                .price(menuItemEntity.getPrice())
                .description(menuItemEntity.getDescription())
                .available(menuItemEntity.getAvailable())
                .subgroup(menuItemEntity.getSubgroup())
                .build();
    }

    public static MenuItemEntity mapDTOToEntity(MenuItemDTO menuItemDTO) {
        return MenuItemEntity.builder()
                .id(menuItemDTO.getId())
                .name(menuItemDTO.getName())
                .price(menuItemDTO.getPrice())
                .description(menuItemDTO.getDescription())
                .available(menuItemDTO.getAvailable())
                .restaurant(RestaurantMapper.mapDTOToEntity(menuItemDTO.getRestaurant()))
                .subgroup(menuItemDTO.getSubgroup())
                .build();
    }
}
