package com.micro_service.OrderService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Cart_Details")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    @Min(value = 1, message = "Product price must be greater than 0")
    private int productPrice;

    @Min(value = 1, message = "Product quantity must be at least 1")
    private int productQuantity;

    @NotBlank(message = "Product description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String productDescription;

    @NotBlank(message = "Product category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String productCategory;

    @NotBlank(message = "Product code is required")
    @Size(max = 50, message = "Product code must not exceed 50 characters")
    @Column(unique = true, nullable = false)
    private String productCode;
}
