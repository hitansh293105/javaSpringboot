package com.example.Ecommerce_Web.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private Key key = Keys.hmacShaKeyFor("flbduskvbsdvugdivdsbgdisuvvdttgjggwrwrqrqri".getBytes());

    public String generateToken(UserDetails userDetails) {

        String username = userDetails.getUsername();

        return Jwts.builder().
                setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration((new Date(new Date().getTime()+ 365L*24*60*60*1000)))
                .signWith(key)
                .compact();
    }

    public Claims extractAllClaims(String Token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(Token).getBody();

    }

    public String extractUsername(String Token) {

        return extractAllClaims(Token).getSubject();
    }

    public boolean istokenExpired(String token) {

        return extractAllClaims(token).getExpiration().before(new Date());

    }

    public boolean validateToken(String token) {

        try {
            return !istokenExpired(token);
        } catch (Exception e) {
            System.out.println("Not validate");
            return false;
        }
    }

           public String getJwtFromRequest (HttpServletRequest request){

               String bearerToken = request.getHeader("Authorization");

               if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                   return bearerToken.substring(7); // Remove "Bearer " prefix
               }

               return null;
           }
       }
