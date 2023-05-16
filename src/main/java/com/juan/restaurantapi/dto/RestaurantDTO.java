package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RestaurantDTO {
    @JsonProperty("restaurant_id")
    private Long id;

    @JsonProperty("name")
    private String restaurantName;
}
