package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepo  extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.userName = ?1")
    Cart findCartByUserName(String userName);

    Cart findByUserUserEmail(String userEmail);
}
