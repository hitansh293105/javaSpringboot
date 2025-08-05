package com.example.Ecommerce_Web.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {

    List<CardItemDto> cardItemDtoList;
}
