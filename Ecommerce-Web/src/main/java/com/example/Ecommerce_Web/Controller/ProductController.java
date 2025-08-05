package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.Dto.ProductDto;
import com.example.Ecommerce_Web.Model.Product;
import com.example.Ecommerce_Web.Service.ProductService;
import com.example.Ecommerce_Web.project.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/api/category/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product, @PathVariable Long categoryId){

        return  ResponseEntity.ok(service.addProduct(product,categoryId));
    }

    @PostMapping("/api/getAll")
    public ResponseEntity<Page<ProductDto>> getAllProduct(@RequestParam(required = false) String keyword,@RequestParam(required = false) String category,
                                                          @RequestParam int pageNo, @RequestParam int pageSize,
                                                          @RequestParam(required = false) String sortBy){


       return  ResponseEntity.ok(service.getAll(pageNo-1,pageSize,keyword,category,sortBy));

    }

    @PostMapping("/api/category/{categoryId}/All")
    public ResponseEntity<String> addAllProductByCategory(@RequestBody ProductRequest productRequest, @PathVariable Long categoryId){


        service.addAll(productRequest.getProducts(),categoryId);
        return ResponseEntity.ok("Added");
    }
}
