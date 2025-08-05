package com.example.Ecommerce_Web.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LoginResponse {

    private String jwtToken;
    private String userName;

}
