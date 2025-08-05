package com.example.Ecommerce_Web.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardItemDto {

    private String productName;
    private double productSpecialPrice;
    private double discount;
    private int quantitynew;
    private long productId;

}
