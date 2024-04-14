package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.CreateOrderDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cors.port}")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDTO> getAllOrders(@PathVariable Long orderId) {
        Optional<OrderDTO> order = orderService.getOrderById(orderId);
        if (order.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(order.get());
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        try {
            Optional<OrderDTO> createdOrder = orderService.createOrder(createOrderDTO);

            if (createdOrder.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder.get());

        } catch (BadRequestException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
