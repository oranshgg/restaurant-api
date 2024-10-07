package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.BillDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cors.port}")
public class BillController {
    private final BillService billService;

    @PostMapping("/bill/{restaurantId}/{tableId}")
    public ResponseEntity<?> requestBill(@PathVariable Long tableId, @PathVariable Long restaurantId) {
        try {
            Optional<BillDTO> bill = billService.requestBill(tableId, restaurantId);
            if (bill.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(bill.get().getTableNumber());
        } catch (BadRequestException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
