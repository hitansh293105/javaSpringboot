package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles,Long> {

    Roles findByName(String name);
}
