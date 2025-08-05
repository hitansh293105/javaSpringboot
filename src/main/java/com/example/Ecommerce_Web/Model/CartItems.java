package com.example.Ecommerce_Web.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    Cart cart;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    @Column(name="product_special_price")
    private double productSpecialPrice;
    private double discount;
}
