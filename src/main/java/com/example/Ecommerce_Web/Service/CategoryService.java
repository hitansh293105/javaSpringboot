package com.example.Ecommerce_Web.Service;

import com.example.Ecommerce_Web.Dto.CategoryDto;
import com.example.Ecommerce_Web.Model.Category;
import com.example.Ecommerce_Web.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo repo;


    public List<CategoryDto> getAll(){

        return repo.findAll().stream().map(this::getCategory).toList();
    }

    public CategoryDto getCategory(Category cate){

        return new CategoryDto(cate.getId(), cate.getName());
    }

    public void create(Category cate){

        repo.save(cate);
    }

    public void deleteByCategoryId(long id){

        repo.deleteById(id);
    }
}
