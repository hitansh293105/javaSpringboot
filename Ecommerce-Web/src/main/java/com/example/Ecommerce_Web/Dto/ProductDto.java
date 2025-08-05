package com.example.Ecommerce_Web.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long productId;
    private String productName;
    private String description;
    private String img;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
