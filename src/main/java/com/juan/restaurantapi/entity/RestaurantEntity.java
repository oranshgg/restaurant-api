package com.juan.restaurantapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
