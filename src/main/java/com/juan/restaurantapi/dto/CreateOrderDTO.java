package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("menu_items_ids")
    private List<Long> menuItems;
}
