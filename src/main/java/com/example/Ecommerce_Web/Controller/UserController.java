package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.MailSender.EmailService;
import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.Repository.UserRepo;
import com.example.Ecommerce_Web.project.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/api/user/resend/code")
    public ResponseEntity<String> sendCode(@RequestBody LoginRequest loginRequest){

       String otp =  emailService.sendMail(loginRequest.getUsername(),"Your OTP Code for Verification");
      User u =   userRepo.findByUserEmail(loginRequest.getUsername()).get();
      u.setOtp(otp);

    return ResponseEntity.ok("Otp has been sent to registered email");


    }

    @GetMapping("/api/user/verify/code")
    public ResponseEntity<String> verifyCode(@RequestParam String email,@RequestParam String otp){

       User u =  userRepo.findByUserEmail(email).get();

       if(u.getOtp().equalsIgnoreCase(otp)) return ResponseEntity.ok("Valid otp");
       else return ResponseEntity.badRequest().body("Invalid Otp Try Again");

    }

    @PostMapping("/api/user/change/password")
    public ResponseEntity<String> changePassword(@RequestBody LoginRequest loginRequest){

      User u =   userRepo.findByUserEmail(loginRequest.getUsername()).get();
      u.setUserPassword(passwordEncoder.encode(loginRequest.getPassword()));

     System.out.println(loginRequest.getPassword());

      userRepo.save(u);
      return ResponseEntity.ok("Password changes sucessfully");
    }

}
