package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dao.TokenDao;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Token;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.exception.PatternMismatchException;
import com.tothenew.ecommerce.exception.UserNotFoundException;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.TokenRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private JavaMailSender javaMailSender;

    @Autowired
    TokenDao tokenDao;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    MailVerification mailVerification;

    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public UserService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void activateUser(Long id)
    {
        User user1 = null;
        String message;
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.getActive()==true)
            {
                message = "user account is already activated";
            }
            else
            {
                user1.setActive(true);
                System.out.println("Sending email for account activation");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getEmail());
                mail.setFrom("kumsag11@gmail.com");
                mail.setSubject("Regarding account activation");
                mail.setText("your account has been activated by admin you can now login");
                System.out.println("now starting");
                javaMailSender.send(mail);
                userRepository.save(user1);
                System.out.println("Email Sent!");
                message = "your account has been activated";
            }
        }
        else
        {
            throw new UserNotFoundException("user with this id is not present");
        }
        System.out.println("message is"+message);
    }

    @Async
    public String  deActivateuser(Long id)
    {
        User user1 = null;
        String message = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.getActive()==false)
            {
                message = "user account is already deactivated";
            }
            else
            {
                user1.setActive(false);
                userRepository.save(user1);
                System.out.println("Sending email...");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getEmail());
                mail.setFrom("kumsag11@gmail.com");
                mail.setSubject("Regarding account deactivation");
                mail.setText("your account has been deactivated by admin you can not login now");
                javaMailSender.send(mail);
                System.out.println("Email Sent!");
                message = "your account has been activated";
            }
        }
        else
        {
            System.out.println("user with this id is not present");
            throw new RuntimeException();
        }
        return message;
    }

    public void forgotPassword(String email_id) {
        User user = userRepository.findByEmail(email_id);
        if (user == null) {
            System.out.println("no user found with this email id");
            throw new RuntimeException();
        } else {
            System.out.println("Sending email...");
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setFrom("kumsag11@gmail.com");
            mail.setSubject("Regarding forgot password");
            String uu = tokenDao.getToken(user);
            mail.setText(uu);
            javaMailSender.send(mail);
            System.out.println("Email Sent!");
        }
    }

    public void setPassword(String token_on_mail, String password) {
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(token_on_mail)) {
                token1 = token;
            }
        }
        if (token1 == null) {
            System.out.println("invalid token");
        } else {
            if (token1.isExpired()) {
                mailVerification.sendNotificaitoin(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
            } else {
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setPassword(new BCryptPasswordEncoder().encode(password));
                userRepository.save(user2);
                tokenRepository.delete(token1);
            }
        }
    }

    public Set<Address> getAddress(String userName){
        User user=userRepository.findByUsername(userName);
        return user.getAddresses();
    }

    public String updateProfile(CustomerDto customer){
        String username=currentUserService.getUser();
        Customer customer1=customerRepository.findByUsername(username);
        if (customer.getFirstName()!=null)
            customer1.setFirstName(customer.getFirstName());
        if (customer.getMiddleName()!=null)
            customer1.setMiddleName(customer.getMiddleName());
        if (customer.getLastName()!=null)
            customer1.setLastName(customer.getLastName());
        if (customer.getContact()!=null)
        {
            if (customer.getContact().toString().matches("(\\+91|0)[0-9]{10}"))
            {
                customer1.setContact(customer.getContact());
            }
            else
            {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
        customerRepository.save(customer1);
        return "success";
    }
}
