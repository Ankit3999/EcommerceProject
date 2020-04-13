package com.tothenew.ecommerce.Mailing;

import com.tothenew.ecommerce.Dao.TokenDao;
import com.tothenew.ecommerce.Entity.Customer;
import com.tothenew.ecommerce.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailVerification {

    private JavaMailSender javaMailSender;

    @Autowired
    TokenDao tokenDao;

    @Autowired
    public MailVerification(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    public void sendNotificaitoin(User user) throws MailException {
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("kumsag11@gmail.com");
        mail.setSubject("To verify account");
        String uu = tokenDao.getToken(user);
        mail.setText(uu);
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }
}