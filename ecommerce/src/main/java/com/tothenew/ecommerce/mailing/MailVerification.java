package com.tothenew.ecommerce.mailing;

import com.tothenew.ecommerce.services.TokenService;
import com.tothenew.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailVerification {

    private JavaMailSender javaMailSender;

    @Autowired
    TokenService tokenService;

    @Autowired
    public MailVerification(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    public void sendNotification(User user) throws MailException {
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("kumsag11@gmail.com");
        mail.setSubject("To verify account");
        String uu = tokenService.getToken(user);
        mail.setText(uu);
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }
}