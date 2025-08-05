package com.example.Ecommerce_Web.Dto;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AddressDto {

    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String buildingName;
    private Long postalCode;
}
