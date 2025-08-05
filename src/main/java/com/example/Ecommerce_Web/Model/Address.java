package com.example.Ecommerce_Web.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String state;
    private String city;
    private String street;
    private Long postalCode;
    private String buildingName;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Orders> ordersList;

    @ManyToOne
    private User user;


}
