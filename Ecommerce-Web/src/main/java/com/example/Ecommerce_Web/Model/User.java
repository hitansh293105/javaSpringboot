package com.example.Ecommerce_Web.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String otp;

    @OneToMany(mappedBy = "user")
    private List<Address> address = new ArrayList<>();

    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Roles> role;

    @OneToOne(mappedBy = "user",cascade=CascadeType.ALL)
    Cart cart;
}
