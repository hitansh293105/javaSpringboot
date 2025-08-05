package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardItemRepo  extends JpaRepository<CartItems,Long> {

    @Query("SELECT c FROM CartItems c WHERE c.cart.id = ?1 AND c.product.productId = ?2")
    CartItems findCartItemByCartIdAndProductId(Long cardId, Long productId);
}
