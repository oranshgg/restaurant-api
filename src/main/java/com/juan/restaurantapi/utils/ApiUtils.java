package com.juan.restaurantapi.utils;

import java.math.BigDecimal;

public class ApiUtils {
    public enum OrderStatus {
        CREATED,
        IN_PROGRESS,
        DELIVERED;
    }
    public static boolean validateOrderStatus(String status) {
        try {
            OrderStatus.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean validateStringPathParameter(String parameter) {
        try {
            Long validation = Long.parseLong(parameter);
            return !parameter.trim().isEmpty() && parameter.length() >= 1;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    public static boolean validateStringParameter(String parameter) {
        return parameter != null && !parameter.trim().isEmpty() && parameter.length() >= 5;
    }

    public static boolean validateBigDecimal(BigDecimal value) {
        if (value == null) {
            return false;
        }

        return value.scale() <= 0;
    }

    public static boolean validateLong(Long value) {
        if (value == null) {
            return false;
        }

        return value > 0;
    }
}
