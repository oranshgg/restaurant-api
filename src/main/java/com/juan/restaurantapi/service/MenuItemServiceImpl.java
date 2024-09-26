package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateMenuItemDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.mapper.MenuItemMapper;
import com.juan.restaurantapi.repository.MenuItemRepository;
import com.juan.restaurantapi.repository.RestaurantRepository;
import com.juan.restaurantapi.utils.ApiUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService{

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Optional<List<MenuItemDTO>> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.getMenuItemsByRestaurantIdNoRestaurantNode(restaurantId);
    }

    @Override
    public Optional<CreateMenuItemDTO> createMenuItem(CreateMenuItemDTO createMenuItemDTO) {

        if (!ApiUtils.validateStringParameter(createMenuItemDTO.getName())) {
            throw new BadRequestException("El nombre del producto no es válido.");
        }

        if (!ApiUtils.validateBigDecimal(createMenuItemDTO.getPrice())) {
            throw new BadRequestException("El precio del producto no es válido.");
        }

        if (!ApiUtils.validateStringParameter(createMenuItemDTO.getDescription())) {
            throw new BadRequestException("La descripción del producto no es válida.");
        }

        if (!ApiUtils.validateLong(createMenuItemDTO.getRestaurantId())) {
            throw new BadRequestException("El id del restaurante del producto no es válido.");
        }

        if (!ApiUtils.validateStringParameter(createMenuItemDTO.getSubgroup())) {
            throw new BadRequestException("El subgrupo del producto no es válido.");
        }

        //valido que el restaurante exista
        Optional<RestaurantDTO> restaurantDTO = restaurantRepository.getRestaurantById(createMenuItemDTO.getRestaurantId());
        if (restaurantDTO.isEmpty()) {
            throw new BadRequestException(
                    String.format("No existe ningún restaurante con el id %d", createMenuItemDTO.getRestaurantId())
            );
        }

        //voy a la BD y creo el item
        Optional<Integer> createdMenuItem = menuItemRepository.createMenuItem(createMenuItemDTO);

        if (createdMenuItem.isEmpty()) {
            throw new BadRequestException("Ocurrió un error al crear el producto.");
        }

        return Optional.of(createMenuItemDTO);
    }

    @Override
    public Optional<MenuItemDTO> updateMenuItem(String menuItemId, CreateMenuItemDTO updateMenuItemDTO) {
        if (!ApiUtils.validateStringPathParameter(menuItemId)) {
            throw new BadRequestException("El id del producto no existe.");
        }

        Optional<MenuItemEntity> menuItemEntity = menuItemRepository.getMenuItemById(Long.parseLong(menuItemId));

        if (menuItemEntity.isEmpty()) {
            throw new BadRequestException("El producto a actualizar no existe.");
        }

        if (updateMenuItemDTO.getName() != null) {
            if (!ApiUtils.validateStringParameter(updateMenuItemDTO.getName())) {
                throw new BadRequestException("El nombre del producto no es válido.");
            }
            menuItemEntity.get().setName(updateMenuItemDTO.getName());
        }

        if (updateMenuItemDTO.getPrice() != null) {
            if (!ApiUtils.validateBigDecimal(updateMenuItemDTO.getPrice())) {
                throw new BadRequestException("El precio del producto no es válido.");
            }
            menuItemEntity.get().setPrice(updateMenuItemDTO.getPrice());
        }

        if (updateMenuItemDTO.getDescription() != null) {
            if (!ApiUtils.validateStringParameter(updateMenuItemDTO.getDescription())) {
                throw new BadRequestException("La descripción del producto no es válida.");
            }
            menuItemEntity.get().setDescription(updateMenuItemDTO.getDescription());
        }

        if (updateMenuItemDTO.getAvailable() != null) {
            menuItemEntity.get().setAvailable(updateMenuItemDTO.getAvailable());
        }

        if (updateMenuItemDTO.getSubgroup() != null) {
            if (!ApiUtils.validateStringParameter(updateMenuItemDTO.getSubgroup())) {
                throw new BadRequestException("El subgrupo del producto no es válido.");
            }
            menuItemEntity.get().setSubgroup(updateMenuItemDTO.getSubgroup());
        }

        Optional<MenuItemEntity> updatedMenuItemEntity = menuItemRepository.updateMenuItem(menuItemEntity.get());

        if (updatedMenuItemEntity.isEmpty()) {
            throw new BadRequestException("Error al actualizar el producto.");
        }

        return Optional.of(MenuItemMapper.mapEntityToDTO(updatedMenuItemEntity.get()));
    }
}
