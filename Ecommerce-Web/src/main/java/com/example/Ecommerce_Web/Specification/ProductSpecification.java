package com.example.Ecommerce_Web.Specification;

import com.example.Ecommerce_Web.Model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {


        public static Specification<Product> hasCategory(String category) {
            return (root, query, cb) -> {
                if (category == null) return null;
                return cb.equal(root.get("cate").get("name"), category);
            };
        }

        public static Specification<Product> hasKeyword(String keyword) {
            return (root, query, cb) -> {
                if (keyword == null) return null;
                return cb.like(cb.lower(root.get("productName")), "%" + keyword.toLowerCase() + "%");
            };
        }
    }


