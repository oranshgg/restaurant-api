package com.juan.restaurantapi.repository;

import com.juan.restaurantapi.dto.CreateMenuItemDTO;
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
        List<MenuItemEntity> restaurantItems = menuItemJPARepository.findMenuItemEntitiesByRestaurantId(restaurantId);

        List<MenuItemDTO> restaurantItemsDTO = new ArrayList<>();

        if (!restaurantItems.isEmpty()) {
            for (MenuItemEntity menuItemEntity : restaurantItems) {
                restaurantItemsDTO.add(MenuItemMapper.mapEntityToDTO(menuItemEntity));
            }
        }
        return Optional.of(restaurantItemsDTO);
    }

    @Override
    public Optional<List<MenuItemDTO>> getMenuItemsByRestaurantIdNoRestaurantNode(Long restaurantId) {
        List<MenuItemEntity> restaurantItems = menuItemJPARepository.findMenuItemEntitiesByRestaurantId(restaurantId);

        List<MenuItemDTO> restaurantItemsDTO = new ArrayList<>();

        if(!restaurantItems.isEmpty()) {
            for (MenuItemEntity menuItemEntity : restaurantItems) {
                restaurantItemsDTO.add(MenuItemMapper.mapEntityToDTOWithoutRestaurant(menuItemEntity));
            }
        }

        return Optional.of(restaurantItemsDTO);
    }

    @Override
    public Optional<MenuItemEntity> getMenuItemById(Long menuItemId) {
        return menuItemJPARepository.findById(menuItemId);
    }

    @Override
    public Optional<Integer> createMenuItem(CreateMenuItemDTO createMenuItemDTO) {
        Integer createdMenuItem = menuItemJPARepository.createMenuItem(
                createMenuItemDTO.getName(),
                createMenuItemDTO.getPrice(),
                createMenuItemDTO.getDescription(),
                createMenuItemDTO.getAvailable(),
                createMenuItemDTO.getRestaurantId(),
                createMenuItemDTO.getSubgroup()
        );
        return Optional.of(createdMenuItem);
    }

    @Override
    public Optional<MenuItemEntity> updateMenuItem(MenuItemEntity menuItemEntityUpdated) {
        return Optional.of(menuItemJPARepository.save(menuItemEntityUpdated));
    }
}
