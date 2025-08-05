package com.example.Ecommerce_Web.Service;

import com.example.Ecommerce_Web.Dto.OrderDto;
import com.example.Ecommerce_Web.Model.*;
import com.example.Ecommerce_Web.Repository.*;
import com.example.Ecommerce_Web.project.AuthUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    AuthUtil authUtil;

    @Value("${spring.key_id}")
    String id;

    @Value("${spring.key_secret}")
    String secret;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    CartRepo cartRepo;



    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ModelMapper modelMapper;

    public OrderDto placeOrder(Long addressId) throws RazorpayException {

     String email = authUtil.loggedInEmail();
     Cart cart = cartRepo.findByUserUserEmail(email);
     if(cart == null) throw new RuntimeException("Cart empty");

     Address address  = addressRepo.findById(addressId).get();

     RazorpayClient client = new RazorpayClient(id, secret);

        JSONObject obj = new JSONObject();
        obj.put("amount",(int)(cart.getTotalPrice()*100));
        obj.put("currency","INR");
        obj.put("receipt","txn_" + UUID.randomUUID());

        Order razorpayOrder = client.orders.create(obj);
        String order_id = razorpayOrder.get("id");

     Orders orders = new Orders();
     orders.setOrderDate(LocalDate.now());
     orders.setOrderStatus("Order accepted");
     orders.setTotalAmount(cart.getTotalPrice());
     orders.setOrderId(order_id);
     orders.setAddress(address);
     orders.setEmail(email);


     Orders updatedorder = orderRepo.save(orders);

     List<CartItems> cartItemsList = cart.getCartItemsList();
     if(cartItemsList.isEmpty()) throw new RuntimeException("CartItem is Empty");

     for(CartItems cartItems : cartItemsList) {

         OrderItem orderItem = new OrderItem();
         orderItem.setOrders(orders);
         orderItem.setProduct(cartItems.getProduct());
         orderItem.setDiscount(cartItems.getDiscount());
         orderItem.setQuantity(cartItems.getQuantity());
         orderItem.setSpecialPrice(cartItems.getProductSpecialPrice());
         orderItemRepo.save(orderItem);
     }

        cart.getCartItemsList().forEach(item->{
            int quantity = item.getQuantity();
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity()-quantity);
            productRepo.save(product);
        });



       return new OrderDto(updatedorder.getId(),updatedorder.getOrderId(), authUtil.loggedInUserName(),email,id);

     }


    public String addPaymentId(String paymentId, String orderId) {

       Orders o =  orderRepo.findByOrderId(orderId);
       o.setPaymentId(paymentId);
       o.setOrderStatus("Paid");

       orderRepo.save(o);
       return "success";

    }
}

