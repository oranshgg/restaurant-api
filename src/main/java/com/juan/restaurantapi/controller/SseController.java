package com.juan.restaurantapi.controller;

import com.juan.restaurantapi.dto.OrderDTO;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class SseController {

    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping("/orders-stream")
    public SseEmitter ordersStream() {
        SseEmitter sseEmitter = new SseEmitter();
        clients.add(sseEmitter);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            clients.remove(sseEmitter);
            sseEmitter.complete();
            executorService.shutdown(); // Shutdown the executor
        }, 300, TimeUnit.SECONDS); // Timeout after 5 minutes

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
