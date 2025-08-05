package com.example.Ecommerce_Web.MailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

      @Autowired
      private JavaMailSender javaMailSender;

    public String sendMail(String toEmail,String subject){

        String value = String.valueOf((int)(Math.random()*10000));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);


        String text = "Your One-Time Password (OTP) for email verification is:\n\n"
                + value + "\n\n"
                + "This code will expire in 2 minutes.\n\n"
                + "If you did not request this code, please ignore this email.\n\n";


        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
        return value;
    }
}
