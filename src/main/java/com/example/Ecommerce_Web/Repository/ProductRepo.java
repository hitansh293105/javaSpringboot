package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Dto.ProductDto;
import com.example.Ecommerce_Web.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo  extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {


}
