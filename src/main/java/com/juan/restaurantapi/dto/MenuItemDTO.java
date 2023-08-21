package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {

    @JsonProperty("id")
    @JsonSetter(nulls = Nulls.SKIP)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("extra_fields")
    private String extraFields;

    @JsonProperty("available")
    private Boolean available;

    @JsonProperty("restaurant")
    private RestaurantDTO restaurant = new RestaurantDTO();
}
