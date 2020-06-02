package com.tothenew.ecommerce.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

    @Autowired
    JavaMailSender mailSender;

    public void sendPasswordResetConfirmationMail(String email) {
        String subject = "Password Reset Successfully";
        String message = "the password for your account has been reset successfully";
        sendEmail(email, subject, message);
    }
    @Async
    public void sendEmail(String email, String subject, String text) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setFrom("kumsag11@gmail.com");
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }
}