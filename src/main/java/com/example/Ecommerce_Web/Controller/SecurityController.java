package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.MailSender.EmailService;
import com.example.Ecommerce_Web.Model.Roles;
import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.project.LoginRequest;
import com.example.Ecommerce_Web.project.LoginResponse;
import com.example.Ecommerce_Web.project.SignUpRequest;
import com.example.Ecommerce_Web.project.SignUpResponse;
import com.example.Ecommerce_Web.Repository.RoleRepo;
import com.example.Ecommerce_Web.Repository.UserRepo;
import com.example.Ecommerce_Web.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils util;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    EmailService emailService;





    @PostMapping("/api/user/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                    , loginRequest.getPassword()));
        } catch (AuthenticationException exception) {

            return new ResponseEntity<>("Login Failed", HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = util.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(token, userDetails.getUsername());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/api/user/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){

        if(userRepo.existsByUserName(signUpRequest.getName())){

            return ResponseEntity.badRequest().body("Username already Exist");

        }
        else if(userRepo.existsByUserEmail(signUpRequest.getEmail())){

            return ResponseEntity.badRequest().body("Email Already exist");
        }

        User user = new User();
        user.setUserName(signUpRequest.getName());
        user.setUserEmail(signUpRequest.getEmail());
        user.setUserPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> roles = signUpRequest.getRole();
        Set<Roles> assignedRoles = new HashSet<>();

        if (roles == null || roles.size() == 0) {
            Roles role = roleRepo.findByName("ROLE_USER");
            assignedRoles.add(role); // assuming assignedRoles is a Set or List to store roles
        } else {
            roles.forEach(roleName -> {
                switch (roleName) {
                    case "ROLE_USER":
                        assignedRoles.add(roleRepo.findByName("ROLE_USER"));
                        break;
                    case "ROLE_ADMIN":
                        assignedRoles.add(roleRepo.findByName("ROLE_ADMIN"));
                        break;
                    // Add more roles as needed
                    default:
                        throw new IllegalArgumentException("Invalid role: " + roleName);
                }
            });
        }

        user.setRole(assignedRoles);
        User u =   userRepo.save(user);

        Set<String> set  = u.getRole().stream().map(roles2 -> roles2.getName()).collect(Collectors.toSet());
        SignUpResponse response = new SignUpResponse(u.getUserName(),u.getUserEmail(),u.getUserPassword(),set);

        return ResponseEntity.ok(response);


    }

    @PostMapping("/api/user/email")
    public ResponseEntity<String> authEmail(@RequestBody LoginRequest loginRequest){

        Optional<User> u   = userRepo.findByUserEmail(loginRequest.getUsername());
        System.out.println("hello");

       if(u.isPresent()) {

          String otp =  emailService.sendMail(loginRequest.getUsername(),"Your OTP Code for Verification");
          User user = u.get();
          user.setOtp(otp);
          userRepo.save(user);

           return ResponseEntity.ok("Otp has been sent to " + loginRequest.getUsername() + " for verification");
       }

       else  return ResponseEntity.badRequest().body("Invalid Email try again");


    }

}
