package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.BillDTO;

import java.util.Optional;

public interface BillService {
    Optional<BillDTO> requestBill(Long tableNumber, Long restaurantId);
}
