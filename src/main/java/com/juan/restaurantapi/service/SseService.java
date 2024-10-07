package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.BillDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.stereotype.Service;

@Service
public interface SseService {
    void sendEvent(OrderDTO orderDTO);

    void sendBillEvent(BillDTO billDTO);
}
