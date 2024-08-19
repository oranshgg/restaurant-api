package com.juan.restaurantapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, Long> requestCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> requestTimestamps = new ConcurrentHashMap<>();
    private final long ALLOWED_REQUESTS = 100; // número de solicitudes permitidas
    private final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1); // ventana de tiempo en mins

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        // Manejo del tiempo
        requestTimestamps.putIfAbsent(clientIp, currentTime);
        long startTime = requestTimestamps.get(clientIp);

        if (currentTime - startTime > TIME_WINDOW) {
            // Reinicia la cuenta y la ventana de tiempo
            requestTimestamps.put(clientIp, currentTime);
            requestCounts.put(clientIp, 1L);
            return true;
        }

        // Manejo del conteo de solicitudes
        long requestCount = requestCounts.merge(clientIp, 1L, Long::sum);

        if (requestCount > ALLOWED_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // Mejor usa 429 para solicitudes excesivas
            return false;
        }

        return true;
    }
}
