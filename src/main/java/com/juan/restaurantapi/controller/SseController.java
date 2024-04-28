package com.juan.restaurantapi.controller;

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

    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping("/orders-stream")
    public SseEmitter ordersStream() {
        SseEmitter sseEmitter = new SseEmitter(0L);
        clients.add(sseEmitter);

        sseEmitter.onTimeout(() -> {
            clients.remove(sseEmitter);
            sseEmitter.complete();
        });

        sseEmitter.onError((ex) -> {
            clients.remove(sseEmitter);
            sseEmitter.complete();
        });

        return sseEmitter;
    }

    @Async
    @EventListener
    public void orderMessageHandler(OrderDTO orderDTO) {
        List<SseEmitter> errorEmitters = new ArrayList<>();

        clients.forEach(emitter -> {
            try {
                emitter.send(orderDTO, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                errorEmitters.add(emitter);
            }
        });

        errorEmitters.forEach(clients::remove);
    }
}
