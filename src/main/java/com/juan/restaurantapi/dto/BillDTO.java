package com.juan.restaurantapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("table_number")
    private Long tableNumber;
}
