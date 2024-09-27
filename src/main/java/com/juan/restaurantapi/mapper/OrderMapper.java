package com.juan.restaurantapi.mapper;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;
import com.juan.restaurantapi.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public final class OrderMapper {

    private OrderMapper() {};

    public static OrderDTO mapEntityToDTO(OrderEntity order) {
        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .restaurant(RestaurantMapper.mapEntityToDTO(order.getRestaurant()))
                .extraFields(order.getExtraFields())
                .tableNumber(order.getTableNumber())
                .status(order.getStatus())
                .build();
    }

    public static OrderDTO mapEntityToDTOWithMenuItems(OrderEntity orderEntity, List<MenuItemEntity> menuItemEntityList) {

        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        for (MenuItemEntity menuItemEntity : menuItemEntityList) {
            menuItemDTOList.add(MenuItemMapper.mapEntityToDTOWithoutRestaurant(menuItemEntity));
        }

        return OrderDTO.builder()
                .id(orderEntity.getId())
                .date(orderEntity.getDate())
                .restaurant(RestaurantMapper.mapEntityToDTO(orderEntity.getRestaurant()))
                .menuItems(menuItemDTOList)
                .extraFields(orderEntity.getExtraFields())
                .tableNumber(orderEntity.getTableNumber())
                .status(orderEntity.getStatus())
                .build();
    }

    public static OrderDTO mapEntityToDTOWithMenuItemsWithoutRestaurant(OrderEntity orderEntity, List<MenuItemEntity> menuItemEntityList) {

        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        for (MenuItemEntity menuItemEntity : menuItemEntityList) {
            menuItemDTOList.add(MenuItemMapper.mapEntityToDTOWithoutRestaurant(menuItemEntity));
        }

        return OrderDTO.builder()
                .id(orderEntity.getId())
                .date(orderEntity.getDate())
                .menuItems(menuItemDTOList)
                .extraFields(orderEntity.getExtraFields())
                .tableNumber(orderEntity.getTableNumber())
                .status(orderEntity.getStatus())
                .build();
    }

    public static OrderEntity mapRequestToEntity(RestaurantEntity restaurantEntity, CreateOrderDTO createOrderDTO) {
        return OrderEntity.builder()
                .restaurant(restaurantEntity)
                .extraFields(createOrderDTO.getExtraFields())
                .tableNumber(createOrderDTO.getTableNumber())
                .status(String.valueOf(ApiUtils.OrderStatus.CREATED))
                .build();
    }
}
