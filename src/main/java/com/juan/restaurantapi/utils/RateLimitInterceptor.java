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
    private final long ALLOWED_REQUESTS = 100; // allowed reqs
    private final long TIME_WINDOW = TimeUnit.MINUTES.toMillis(1); // time window on mins

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        // time handling
        requestTimestamps.putIfAbsent(clientIp, currentTime);
        long startTime = requestTimestamps.get(clientIp);

        if (currentTime - startTime > TIME_WINDOW) {
            // restart the count and time window
            requestTimestamps.put(clientIp, currentTime);
            requestCounts.put(clientIp, 1L);
            return true;
        }

        // handle count reqs
        long requestCount = requestCounts.merge(clientIp, 1L, Long::sum);

        if (requestCount > ALLOWED_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }

        return true;
    }
}
