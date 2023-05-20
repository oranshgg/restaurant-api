package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    @JsonProperty("restaurant_id")
    private Long restaurant_id;
}
