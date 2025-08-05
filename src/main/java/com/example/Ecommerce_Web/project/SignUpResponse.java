package com.example.Ecommerce_Web.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpResponse {

   private String name;
   private  String  email;
   private String password;
   private Set<String> role;

}
