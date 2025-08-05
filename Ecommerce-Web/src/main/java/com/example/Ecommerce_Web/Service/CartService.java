package com.example.Ecommerce_Web.Service;

import com.example.Ecommerce_Web.Dto.CardItemDto;
import com.example.Ecommerce_Web.Dto.CartDto;
import com.example.Ecommerce_Web.Model.Cart;
import com.example.Ecommerce_Web.Model.CartItems;
import com.example.Ecommerce_Web.Model.Product;
import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.project.AuthUtil;
import com.example.Ecommerce_Web.Repository.CardItemRepo;
import com.example.Ecommerce_Web.Repository.CartRepo;
import com.example.Ecommerce_Web.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    CardItemRepo cartItemRepo;

    @Autowired

    public CartDto getCartByUser(){

        String userName = authUtil.loggedInUserName();

        if(userName == null) return null;

        Cart cart = cartRepo.findCartByUserName(userName);

      List<CardItemDto> cardItemDtos =   cart.getCartItemsList().stream().map(this::getCardItemsDto).toList();

      return new CartDto(cart.getCardId(),userName,cart.getTotalPrice(),cardItemDtos);

    }

    public List<CartDto> getAllCart(){

     return cartRepo.findAll().stream().map(this::getCardDto).toList();

    }

    private CartDto getCardDto(Cart cart) {

      List<CardItemDto>  cardItemDtoList =   cart.getCartItemsList().stream().map(cartItems -> new CardItemDto(cartItems.getProduct().getProductName()
        ,cartItems.getProductSpecialPrice(),cartItems.getDiscount(),cartItems.getQuantity(),cartItems.getProduct().getProductId())).toList();

      return new CartDto(cart.getCardId(),cart.getUser().getUserName(), cart.getTotalPrice(),cardItemDtoList);


    }

    public CartDto addProductToCart(Long productId, Integer quantity){


        Cart cart = createCart();


        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock available");
        }


        CartItems cartItems = cartItemRepo.findCartItemByCartIdAndProductId(cart.getCardId(), productId);

         if(cartItems!=null) throw new RuntimeException();
         else {
            // Product not in cart - create new cart item
            cartItems = new CartItems();
            cartItems.setProduct(product);
            cartItems.setCart(cart);
            cartItems.setQuantity(quantity);
            cartItems.setProductSpecialPrice(product.getSpecialPrice());
            cartItems.setDiscount(product.getDiscount());
        }

        // Step 5: Save the cart item
        cartItemRepo.save(cartItems);

        cart.setTotalPrice(cart.getTotalPrice() + product.getSpecialPrice()*quantity);

        cart.getCartItemsList().add(cartItems);

       Cart cart1 =   cartRepo.save(cart);

      List<CardItemDto>  cardItemDtos = cart1.getCartItemsList().stream().map(this::getCardItemsDto).toList();

      return new CartDto(cart1.getCardId(),cart1.getUser().getUserName(),cart1.getTotalPrice(),cardItemDtos);

    }

    private CardItemDto getCardItemsDto(CartItems cartItems) {

        return new CardItemDto(cartItems.getProduct().getProductName(),cartItems.getProductSpecialPrice(),cartItems.getDiscount()
        ,cartItems.getQuantity(),cartItems.getProduct().getProductId());
    }

    public Cart createCart(){

        String username = authUtil.loggedInUserName();

        if(username == null) return null;

        Cart cart = cartRepo.findCartByUserName(username);

        if(cart!=null) return cart;

        Cart cart1 = new Cart();
        cart1.setTotalPrice(0.0);
        cart1.setUser(authUtil.loggedInUser());

        return cartRepo.save(cart1);

    }

    @Transactional
    public CartDto updateProductQuantityInCart(Long productId, int quantity) {

        String userName = authUtil.loggedInUserName();
        if(userName == null) return null;
        Cart cart = cartRepo.findCartByUserName(userName);

       Product product = productRepo.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));

        if(product.getQuantity() < quantity) throw new RuntimeException("Quantity lesser than expected");

       CartItems cartItems = cartItemRepo.findCartItemByCartIdAndProductId(cart.getCardId(),productId);

       if(cartItems == null) throw new RuntimeException("No cardItems exist");

       cartItems.setQuantity(cartItems.getQuantity() + quantity);
       cartItems.setProductSpecialPrice(product.getSpecialPrice());

       cart.setTotalPrice(cart.getTotalPrice() + cartItems.getProductSpecialPrice()*quantity);
       cartRepo.save(cart);

       CartItems updatedItem = cartItemRepo.save(cartItems);
       if(updatedItem.getQuantity() == 0) cartItemRepo.deleteById(updatedItem.getCartItemId());

        List<CardItemDto>  cardItemDtos = cart.getCartItemsList().stream().map(this::getCardItemsDto).toList();

        return new CartDto(cart.getCardId(),cart.getUser().getUserName(),cart.getTotalPrice(),cardItemDtos);

    }

    public String deleteProductFromCart(Long productId) {

        String userName = authUtil.loggedInEmail();

        if(userName == null) return null;

        Cart cart = cartRepo.findCartByUserName(userName);

        CartItems cartItem = cartItemRepo.findCartItemByCartIdAndProductId(cart.getCardId(), productId);
        if (cartItem == null) {
            throw new RuntimeException("No cart item found for product ID: " + productId);
        }

        double deduction = cartItem.getProductSpecialPrice()* cartItem.getQuantity();
        cart.setTotalPrice(cart.getTotalPrice() - deduction);

        cart.getCartItemsList().remove(cartItem);
        cartRepo.save(cart);



        return "Product deleted from cart successfully";
    }

    public String addCart(List<CardItemDto> cardItemDtoList){

       if(authUtil.loggedInEmail() == null) return "not found";

       String email = authUtil.loggedInEmail();


      Cart cart = cartRepo.findByUserUserEmail(email);

      if(cart == null){
          cart = new Cart();
          cart.setTotalPrice(0.0);
          cart.setUser(authUtil.loggedInUser());
          cartRepo.save(cart);
      }
      else{

          cart.getCartItemsList().clear();
          cartRepo.save(cart);

      }
      double totalPrice = 0;

      for(CardItemDto cardItemDto : cardItemDtoList){

           Long productId = cardItemDto.getProductId();
           Integer quantity = cardItemDto.getQuantitynew();

           Product product = productRepo.findById(productId).get();

           CartItems cartItems = new CartItems();
          cartItems.setProductSpecialPrice(product.getSpecialPrice());
          cartItems.setDiscount(product.getDiscount());
          cartItems.setQuantity(quantity);
          cartItems.setCart(cart);
          cartItems.setProduct(product);

          totalPrice += product.getSpecialPrice()*quantity;
          cartItemRepo.save(cartItems);

      }
      System.out.println(totalPrice);
      cart.setTotalPrice(totalPrice);
      cartRepo.save(cart);

        return "CartItems added to cart";
    }

    }