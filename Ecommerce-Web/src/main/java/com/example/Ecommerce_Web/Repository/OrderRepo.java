package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.Orders;
import com.razorpay.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo  extends JpaRepository<Orders,Long> {

    @Query("SELECT o FROM Orders o WHERE o.orderId=:orderId")
    Orders findByOrderId(String orderId);
}
