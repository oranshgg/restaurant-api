package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.BillDTO;
import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@CrossOrigin(origins = "${cors.port}")
public class SseController {

    private final Set<SseEmitter> orderClients = new CopyOnWriteArraySet<>();
    private final Set<SseEmitter> billClients = new CopyOnWriteArraySet<>();

    @GetMapping("/orders-stream")
    public SseEmitter ordersStream() {
        return createSseEmitter(orderClients);
    }

    @GetMapping("/bills-stream")
    public SseEmitter billsStream() {
        return createSseEmitter(billClients);
    }

    private SseEmitter createSseEmitter(Set<SseEmitter> clientSet) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        clientSet.add(sseEmitter);

        sseEmitter.onTimeout(() -> {
            clientSet.remove(sseEmitter);
            sseEmitter.complete();
        });

        sseEmitter.onError((ex) -> {
            clientSet.remove(sseEmitter);
            sseEmitter.complete();
        });

        return sseEmitter;
    }

    @Async
    @EventListener
    public void orderMessageHandler(OrderDTO orderDTO) {
        sendToClients(orderClients, orderDTO);
    }

    @Async
    @EventListener
    public void billMessageHandler(BillDTO billDTO) {
        sendToClients(billClients, billDTO);
    }

    private <T> void sendToClients(Set<SseEmitter> clients, T event) {
        List<SseEmitter> errorEmitters = new ArrayList<>();

        clients.forEach(emitter -> {
            try {
                emitter.send(event, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                errorEmitters.add(emitter);
            }
        });

        errorEmitters.forEach(clients::remove);
    }
}
