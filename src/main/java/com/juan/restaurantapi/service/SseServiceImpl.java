package com.juan.restaurantapi.service;

import com.juan.restaurantapi.dto.BillDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SseServiceImpl implements SseService {

    private final ApplicationEventPublisher publisher; //1

    public SseServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEvent(OrderDTO orderDTO) {
        publisher.publishEvent(orderDTO);
    }

    public void sendBillEvent(BillDTO billDTO) {
        publisher.publishEvent(billDTO);
    }
}
