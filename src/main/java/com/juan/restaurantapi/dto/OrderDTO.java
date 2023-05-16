package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @JsonProperty("id")
    @JsonSetter(nulls = Nulls.SKIP)
    private Long id;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("restaurant")
    private RestaurantDTO restaurant = new RestaurantDTO();
}
