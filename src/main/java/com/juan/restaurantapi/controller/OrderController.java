package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.events.EventProducer;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final EventProducer eventProducer;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        Optional<List<OrderDTO>> ordersList = orderService.getAllOrders();

        if (ordersList.isEmpty() || ordersList.get().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersList.get());
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        try {
            Optional<OrderDTO> createdOrder = orderService.createOrder(createOrderDTO);

            if (createdOrder.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            eventProducer.publishEvent(createdOrder.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder.get());

        } catch (BadRequestException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

}
