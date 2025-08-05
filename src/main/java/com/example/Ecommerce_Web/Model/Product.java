package com.example.Ecommerce_Web.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String productName;
    private String description;
    private String img;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    private Category cate;

    @OneToMany(mappedBy = "product")
    private List<CartItems> products;


}
