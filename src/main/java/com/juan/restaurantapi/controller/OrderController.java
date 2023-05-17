package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.OrderDTO;
import com.juan.restaurantapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        Optional<List<OrderDTO>> ordersList = orderService.getAllOrders();

        if (ordersList.isEmpty() || ordersList.get().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersList.get());
    }

}
