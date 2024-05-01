package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.juan.restaurantapi.entity.MenuItemEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @JsonProperty("id")
    @JsonSetter(nulls = Nulls.SKIP)
    private Long id;

    @JsonProperty("date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime date;

    @JsonProperty("extra_fields")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String extraFields;

    @JsonProperty("menu_items")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItemDTO> menuItems = new ArrayList<>();

    @JsonProperty("restaurant")
    private RestaurantDTO restaurant = new RestaurantDTO();

    @JsonProperty("table_number")
    private Long tableNumber;
}
