package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuItemDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("available")
    private Boolean available;

    @JsonProperty("subgroup")
    private String subgroup;
}
