package com.micro_service.OrderService.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "order_service")
@Data
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NotBlank(message = "Order name is required")
    @Size(max = 100, message = "Order name must not exceed 100 characters")
    private String orderName;

    @NotBlank(message = "Order address is required")
    @Size(max = 255, message = "Order address must not exceed 255 characters")
    private String orderAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // to avoid join table, adds order_id column in ProductEntity
    @Valid
    private List<ProductEntity> order;
}
