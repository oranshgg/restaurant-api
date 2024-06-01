package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("available")
    private Boolean available;

    @JsonProperty("restaurant")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RestaurantDTO restaurant = new RestaurantDTO();

    @JsonProperty("subgroup")
    private String subgroup;
}
