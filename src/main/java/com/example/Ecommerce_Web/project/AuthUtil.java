package com.example.Ecommerce_Web.project;

import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    UserRepo userRepo;

    public String loggedInUserName(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) return null;

        User user =  userRepo.findByUserEmail(authentication.getName()).get();

        return user.getUserName();
    }
    public User loggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) return null;

        return userRepo.findByUserEmail(authentication.getName()).get();

    }

    public String loggedInEmail(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return null;

        return authentication.getName();

    }

}
