package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
