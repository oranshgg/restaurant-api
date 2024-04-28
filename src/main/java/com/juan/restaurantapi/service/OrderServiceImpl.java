package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.MenuItemDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.dto.RestaurantDTO;
import com.juan.restaurantapi.entity.MenuItemEntity;
import com.juan.restaurantapi.entity.OrderEntity;
import com.juan.restaurantapi.entity.OrderItemEntity;
import com.juan.restaurantapi.entity.RestaurantEntity;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.mapper.MenuItemMapper;
import com.juan.restaurantapi.mapper.OrderMapper;
import com.juan.restaurantapi.mapper.RestaurantMapper;
import com.juan.restaurantapi.repository.MenuItemRepository;
import com.juan.restaurantapi.repository.OrderItemsRepository;
import com.juan.restaurantapi.repository.OrderRepository;
import com.juan.restaurantapi.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final SseService sseService;

    @Override
    public Optional<OrderDTO> getOrderById(Long orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.getOrderById(orderId);

        if (orderEntity.isEmpty()) {
            return Optional.empty();
        }

        Optional<List<OrderItemEntity>> orderItemEntities = orderItemsRepository.getMenuItemsByOrderId(orderId);

        List<Long> menuItemsIds = new ArrayList<>();

        for (OrderItemEntity orderItemEntity : orderItemEntities.get()) {
            menuItemsIds.add(orderItemEntity.getMenuItemId().getId());
        }

        List<MenuItemEntity> menuItemEntities = this.getMenuItemEntities(menuItemsIds);

        return Optional.of(
            OrderMapper.mapEntityToDTOWithMenuItems(orderEntity.get(), menuItemEntities)
        );
    }

    @Override
    public Optional<OrderDTO> createOrder(CreateOrderDTO createOrderDTO) {

        if (createOrderDTO.getTableNumber() == null || createOrderDTO.getTableNumber() <= 0) {
            throw new BadRequestException("El numero de mesa no es válido.");
        }

        Optional<RestaurantDTO> restaurantDTO = restaurantRepository.getRestaurantById(createOrderDTO.getRestaurantId());
        if (restaurantDTO.isEmpty()) {
            throw new BadRequestException(
                String.format("No existe ningún restaurante con el id %d", createOrderDTO.getRestaurantId())
            );
        }

        Optional<List<MenuItemDTO>> menuList = menuItemRepository.getMenuItemsByRestaurantId(createOrderDTO.getRestaurantId());
        if (menuList.orElse(new ArrayList<>()).isEmpty()) {
            throw new BadRequestException(
                    String.format("No existen items para el restaurante con el id %d", createOrderDTO.getRestaurantId())
            );
        }

        List<Long> allRestaurantMenuItems = this.getIdsFromMenuItemList(menuList);

        boolean idsExist = this.validateRequestMenuItemsIds(allRestaurantMenuItems, createOrderDTO.getMenuItems());

        if (!idsExist) {
            throw new BadRequestException("Hay menu items que no existen");
        }

        List<MenuItemEntity> menuItemEntities = this.getMenuItemEntitiesFromRequest(menuList, createOrderDTO.getMenuItems());

        boolean areAvailable = this.validateReqMenuItemsStatus(menuItemEntities);

        if (!areAvailable) {
            throw new BadRequestException("Hay menu items no disponibles");
        }

        RestaurantEntity restaurantEntity = RestaurantMapper.mapDTOToEntity(restaurantDTO.get());
        OrderEntity orderEntity = OrderMapper.mapRequestToEntity(restaurantEntity, createOrderDTO);

        Optional<OrderEntity> createdOrder = orderRepository.createOrder(orderEntity);

        if (createdOrder.isEmpty()) {
            return Optional.empty();
        }

        //guardar la relación order - menu item
        orderItemsRepository.saveMenuItems(createdOrder.get(), menuItemEntities);
        //mandamos el evento a /orders-stream
        sseService.sendEvent(OrderMapper.mapEntityToDTO(createdOrder.get()));

        return Optional.of(OrderMapper.mapEntityToDTO(createdOrder.get()));
    }

    private List<MenuItemEntity> getMenuItemEntities(List<Long> menuItemsIds) {
        List<MenuItemEntity> menuItemEntities = new ArrayList<>();
        for (Long id : menuItemsIds) {
            Optional<MenuItemEntity> menuItemEntity = menuItemRepository.getMenuItemById(id);
            menuItemEntity.ifPresent(menuItemEntities::add);
        }
        return menuItemEntities;
    }

    private List<MenuItemEntity> getMenuItemEntitiesFromRequest(
            Optional<List<MenuItemDTO>> menuList,
            List<Long> requestMenuItems) {

        List<MenuItemEntity> menuItemEntities = new ArrayList<>();
        for(MenuItemDTO menuItemDTO : menuList.get()) {
            if (requestMenuItems.contains(menuItemDTO.getId())) {
                menuItemEntities.add(MenuItemMapper.mapDTOToEntity(menuItemDTO));
            }
        }
        return menuItemEntities;
    }

    private List<Long> getIdsFromMenuItemList(Optional<List<MenuItemDTO>> menuList) {
        List<Long> menuItemIds = new ArrayList<>();
        for (MenuItemDTO menuItem : menuList.get()) {
            menuItemIds.add(menuItem.getId());
        }
        return menuItemIds;
    }

    private boolean validateRequestMenuItemsIds(List<Long> DBMenuItemsIds, List<Long> menuItemsIdsRequest) {
        boolean continueCreation = true;
        for (Long id : menuItemsIdsRequest) {
            if(!DBMenuItemsIds.contains(id)) {
                continueCreation = false;
                break;
            }
        }
        return continueCreation;
    }

    private boolean validateReqMenuItemsStatus(List<MenuItemEntity> menuItemEntityList) {
        for(MenuItemEntity menuItemEntity : menuItemEntityList) {
         if (!menuItemEntity.getAvailable()) {
             return false;
         }
        }
        return true;
    }
}
