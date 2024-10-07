package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.BillDTO;
import com.juan.restaurantapi.exception.BadRequestException;
import com.juan.restaurantapi.utils.ApiUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final SseService sseService;
    @Override
    public Optional<BillDTO> requestBill(Long tableNumber, Long restaurantId) {
        if (!ApiUtils.validateLong(restaurantId)) {
            throw new BadRequestException("El numero de restaurante no es válido.");
        }
        if (!ApiUtils.validateLong(tableNumber)) {
            throw new BadRequestException("El numero de mesa no es válido.");
        }
        BillDTO billDTO = BillDTO.builder().restaurantId(restaurantId).tableNumber(tableNumber).build();
        sseService.sendBillEvent(billDTO);
        return Optional.of(billDTO);
    }
}
