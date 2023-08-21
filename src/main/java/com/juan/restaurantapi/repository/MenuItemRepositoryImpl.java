package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.mapper.MenuItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final MenuItemJPARepository menuItemJPARepository;

    @Override
    public Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId) {
          List<MenuItemEntity> restaurantItems = menuItemJPARepository
                          .findAll()
                          .stream()
                          .filter(menuItemEntity -> menuItemEntity.getRestaurant().getId().equals(restaurantId))
                          .toList();

        List<MenuItemDTO> restaurantItemsDTO = new ArrayList<>();

        if (!restaurantItems.isEmpty()) {
            for (MenuItemEntity menuItemEntity : restaurantItems) {
                restaurantItemsDTO.add(MenuItemMapper.mapEntityToDTO(menuItemEntity));
            }
        }
        return Optional.of(restaurantItemsDTO);
    }
}
