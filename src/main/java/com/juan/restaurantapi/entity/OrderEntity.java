package com.juan.restaurantapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "date", insertable = false, updatable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @Column(name = "extra_fields")
    private String extraFields;

    @Column(name = "table_number")
    private Long tableNumber;

    @Column(name = "status")
    private String status;
}
