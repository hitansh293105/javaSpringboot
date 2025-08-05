package com.example.Ecommerce_Web;

import com.example.Ecommerce_Web.Model.Roles;
import com.example.Ecommerce_Web.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {

        Roles role1 = new Roles();
        role1.setName("ROLE_USER");

        Roles role2 = new Roles();
        role2.setName("ROLE_ADMIN");

        if(roleRepo.count() == 0)  roleRepo.saveAll(Arrays.asList(role1,role2));


    }
}
