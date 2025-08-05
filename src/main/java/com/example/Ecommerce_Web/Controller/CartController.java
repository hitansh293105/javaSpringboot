package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.Dto.CardItemDto;
import com.example.Ecommerce_Web.Dto.CardRequestDto;
import com.example.Ecommerce_Web.Dto.CartDto;
import com.example.Ecommerce_Web.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity){

        CartDto cartDto = cartService.addProductToCart(productId,quantity);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("carts/getAll")
    public ResponseEntity<List<CartDto>> getAll(){

        return ResponseEntity.ok(cartService.getAllCart());


    }

    @PostMapping("carts/user")
    public ResponseEntity<CartDto> getCartByUser(){

        return ResponseEntity.ok(cartService.getCartByUser());
    }

    @PutMapping("cart/product/{productId}/quantity/{operation}")
    public ResponseEntity<CartDto> updateCardProduct(@PathVariable Long productId,@PathVariable String operation){

     return ResponseEntity.ok(
             cartService.updateProductQuantityInCart(productId,operation.equalsIgnoreCase("delete")?-1:1));

    }

    @DeleteMapping("/cart/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long productId){

        return ResponseEntity.ok(cartService.deleteProductFromCart(productId));

    }

    @PostMapping("/Add/cart/product")
    public ResponseEntity<String> addCart(@RequestBody CardRequestDto cardItemDtoList){

        return ResponseEntity.ok(cartService.addCart(cardItemDtoList.getCardItemDtoList()));
    }


}
