package com.example.Ecommerce_Web.Controller;
import com.example.Ecommerce_Web.Dto.CategoryDto;
import com.example.Ecommerce_Web.Model.Category;
import com.example.Ecommerce_Web.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping("/api/user/getAllCategory")
    public List<CategoryDto> getAll(){

        return service.getAll();
    }

    @PostMapping("/api/user/create")
    public String  createCategory(@RequestBody Category cate){

        service.create(cate);
        return "category added successfully";
    }

    @DeleteMapping("/api/user/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){

        List<CategoryDto> category = service.getAll();

        Optional<CategoryDto> categoryOpt = category.stream()
                .filter(cate ->cate.getCategoryId().equals(id))
                .findFirst();

        System.out.println("hello");

        if (categoryOpt.isPresent()) {
            service.deleteByCategoryId(id);
            return ResponseEntity.ok("Category deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with id " + id);
        }


    }
}
