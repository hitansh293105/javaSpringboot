package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<User,Long> {

    public Optional<User> findByUserName(String userName);

    public Optional<User> findByUserEmail(String userEmail);


    public boolean existsByUserName(String userName);

    public boolean existsByUserEmail(String userEmail);



}
