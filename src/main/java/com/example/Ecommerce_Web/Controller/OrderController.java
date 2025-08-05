package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.Dto.OrderDto;
import com.example.Ecommerce_Web.Service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/user/payment/{addressId}")
    public ResponseEntity<OrderDto> orderProduct(@PathVariable Long addressId) throws RazorpayException {

       return ResponseEntity.ok(orderService.placeOrder(addressId));

    }
    @PostMapping("/order/paymentId/orderId")
    public ResponseEntity<String> hello(@RequestParam String paymentId,@RequestParam String orderId){

        return ResponseEntity.ok(orderService.addPaymentId(paymentId,orderId));


    }
}
