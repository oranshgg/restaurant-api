package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.entity.MenuItemEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemJPARepository menuItemJPARepository;

    @Override
    //TODO: hacer que esto retorne un Optional<List<MenuItemDTO>>
    public List<MenuItemEntity> getMenuItemsByRestaurantId(Long restaurantId) {
          List<MenuItemEntity> menuItems = menuItemJPARepository.findAll();
        for (MenuItemEntity menuItem : menuItems) {
            System.out.println("MENU ITEM: " + menuItem);
        }
      return menuItems;
    }

}
