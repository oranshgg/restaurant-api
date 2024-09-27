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
import com.juan.restaurantapi.utils.ApiUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        if (orderId < 1) {
            throw new BadRequestException(
                    String.format("El order id es inválido")
            );
        }

        Optional<OrderEntity> orderEntity = orderRepository.getOrderById(orderId);

        if (orderEntity.isEmpty()) {
            return Optional.empty();
        }

        Optional<List<OrderItemEntity>> orderItemEntities = orderItemsRepository.getOrderItemsByOrderId(orderId);

        List<Long> menuItemsIds = new ArrayList<>();

        for (OrderItemEntity orderItemEntity : orderItemEntities.get()) {
            menuItemsIds.add(orderItemEntity.getMenuItemId().getId());
        }

        List<MenuItemEntity> menuItemEntities = this.getMenuItemEntities(menuItemsIds);

        return Optional.of(
            OrderMapper.mapEntityToDTOWithMenuItems(orderEntity.get(), menuItemEntities)
        );
    }

    public Optional<List<OrderDTO>> getLastOrders(Long restaurantId) {
        if (restaurantId < 1) {
            throw new BadRequestException(
                    String.format("El restaurant id es inválido")
            );
        }

        Optional<List<OrderEntity>> orderEntities = orderRepository.getLastOrders(restaurantId);

        if (orderEntities.isEmpty()) {
            return Optional.empty();
        }

        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Long> menuItemsIds = new ArrayList<>();
        List<MenuItemEntity> menuItemEntities;
        List<Long> orderIds = this.getOrderIdsFromOrderEntities(orderEntities.get());
        Optional<List<OrderItemEntity>> orderItemEntities = orderItemsRepository.getOrderItemsByOrderIdList(orderIds);

        for (OrderEntity orderEntity : orderEntities.get()) {
            menuItemsIds.clear();
            for (OrderItemEntity orderItemEntity : orderItemEntities.get()) {
                if (orderItemEntity.getOrderId().getId().equals(orderEntity.getId())) {
                    menuItemsIds.add(orderItemEntity.getMenuItemId().getId());
                }
            }
            menuItemEntities = this.getMenuItemEntities(menuItemsIds);
            OrderDTO orderDTO = OrderMapper.mapEntityToDTOWithMenuItemsWithoutRestaurant(orderEntity, menuItemEntities);
            orderDTOList.add(orderDTO);
        }

        if (orderDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orderDTOList);
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

        if (createOrderDTO.getMenuItems().size() > 20) {
            throw new BadRequestException("Máximo de items alcanzado");
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
        orderItemsRepository.saveOrderItems(createdOrder.get(), menuItemEntities);
        //mandamos el evento a /orders-stream
        sseService.sendEvent(OrderMapper.mapEntityToDTOWithMenuItems(createdOrder.get(), menuItemEntities));

        return Optional.of(OrderMapper.mapEntityToDTO(createdOrder.get()));
    }

    @Override
    public Optional<OrderDTO> updateOrder(Long orderId, String status) {

        if (!ApiUtils.validateOrderStatus(status)) {
            throw new BadRequestException("No se puede setear ese estado a la orden");
        }

        if (!ApiUtils.validateLong(orderId)) {
            throw new BadRequestException("El id de la orden no es válido");
        }

        Optional<OrderEntity> orderEntity = orderRepository.getOrderById(orderId);

        if (orderEntity.isEmpty()) {
            throw new BadRequestException("La orden a actualizar no existe.");
        }

        orderEntity.get().setStatus(status);

        Optional<OrderEntity> updatedOrderEntity = orderRepository.createOrder(orderEntity.get());
        if (updatedOrderEntity.isEmpty()) {
            throw new BadRequestException("Error al actualizar el estado de la orden");
        }

        return Optional.of(OrderMapper.mapEntityToDTO(updatedOrderEntity.get()));
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

        for (Long id : requestMenuItems) {
            for (MenuItemDTO menuItemDTO : menuList.get()) {
                if (menuItemDTO.getId().equals(id)) {
                    menuItemEntities.add(MenuItemMapper.mapDTOToEntity(menuItemDTO));
                }
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
            if (!DBMenuItemsIds.contains(id)) {
                continueCreation = false;
                break;
            }
        }
        return continueCreation;
    }

    private boolean validateReqMenuItemsStatus(List<MenuItemEntity> menuItemEntityList) {
        for (MenuItemEntity menuItemEntity : menuItemEntityList) {
         if (!menuItemEntity.getAvailable()) {
             return false;
         }
        }
        return true;
    }

    private List<Long> getOrderIdsFromOrderEntities(List<OrderEntity> orderEntities) {
        List<Long> orderIds = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            orderIds.add(orderEntity.getId());
        }
        return orderIds;
    }
}
