package com.example.Ecommerce_Web.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SignUpRequest {

    private String email;
    private String name;
    private String password;
    private Set<String> role;

}
