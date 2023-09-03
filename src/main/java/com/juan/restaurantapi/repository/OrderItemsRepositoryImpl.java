package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderItemsRepositoryImpl implements OrderItemsRepository{

    private final OrderItemsJPARepository orderItemsJPARepository;
    @Override
    public void saveMenuItems(OrderEntity orderEntity, List<MenuItemEntity> menuItemEntities) {
        for(MenuItemEntity menuItemEntity : menuItemEntities) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderId(orderEntity);
            orderItemEntity.setMenuItemId(menuItemEntity);
           orderItemsJPARepository.save(orderItemEntity);
        }
    }

    @Override
    public Optional<List<OrderItemEntity>> getMenuItemsByOrderId(Long orderId) {
        return Optional.of(orderItemsJPARepository.findMenuItemsByOrderId(orderId));
    }
}
