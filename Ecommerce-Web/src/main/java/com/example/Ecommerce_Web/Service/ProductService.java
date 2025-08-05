package com.example.Ecommerce_Web.Service;

import com.example.Ecommerce_Web.Dto.ProductDto;
import com.example.Ecommerce_Web.Model.Category;
import com.example.Ecommerce_Web.Model.Product;
import com.example.Ecommerce_Web.Repository.CategoryRepo;
import com.example.Ecommerce_Web.Repository.ProductRepo;

import com.example.Ecommerce_Web.Specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    @Autowired
    private CategoryRepo cateRepo;

    public ProductDto addProduct(Product p, Long CategoryId){

        Category category = cateRepo.findById(CategoryId)
                .orElseThrow(()-> new RuntimeException("category not found"));

        p.setCate(category);
        double specialPrice = p.getPrice() - (p.getDiscount()*0.01)*p.getPrice();
        p.setSpecialPrice(specialPrice);

       Product pr =  repo.save(p);

       return new ProductDto(pr.getProductId(),pr.getProductName(),pr.getDescription(),pr.getImg(),pr.getQuantity(),pr.getPrice(),pr.getDiscount(),pr.getSpecialPrice());
    }

    public Page<ProductDto> getAll(int pageNo,int pageSize,String keyword,String category,String sortBy){

        Pageable pageable;
        Sort.Direction direction;

        if(sortBy!=null){

            if(sortBy.equalsIgnoreCase("asc")) direction  = Sort.Direction.ASC;
            else direction = Sort.Direction.DESC;

           Sort sort = Sort.by(direction,"price");
           pageable = PageRequest.of(pageNo,pageSize,sort);
        }

        else{

            pageable = PageRequest.of(pageNo,pageSize);
        }

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasKeyword(keyword));



       Page<Product> productPage = repo.findAll(spec,pageable);

       if(pageNo > productPage.getTotalPages()){

           pageable = PageRequest.of(0,pageSize);
          return repo.findAll(spec,pageable).map(this::getProductDto);
       }

       else return productPage.map(this::getProductDto);

    }

    private ProductDto  getProductDto(Product product) {

        System.out.println("hello");
        System.out.println(product.getProductName());
        return new ProductDto(product.getProductId(),product.getProductName(),product.getDescription()
                ,product.getImg(),product.getQuantity(),product.getPrice(),product.getDiscount(), product.getSpecialPrice());


    }

    public void addAll(List<Product> products, Long categoryId) {

        products.forEach(p -> {
            Category category = cateRepo.findById(categoryId)
                    .orElseThrow(()-> new RuntimeException("category not found"));

            p.setCate(category);
            double specialPrice = p.getPrice() - (p.getDiscount()*0.01)*p.getPrice();
            p.setSpecialPrice(specialPrice);

            Product pr =  repo.save(p);

        });
    }


}
