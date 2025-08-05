package com.example.Ecommerce_Web.Security;

import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user =  repo.findByUserEmail(username).orElseThrow(()-> new RuntimeException("Username not found"));

       return UserDetailsImp.build(user);


    }
}
