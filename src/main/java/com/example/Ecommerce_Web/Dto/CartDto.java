package com.example.Ecommerce_Web.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private long cardId;
    private String userName;
    private double totalPrice;
    private List<CardItemDto> productList;

}
