package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.SellerDto;
import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.exception.EmailAlreadyExistsException;
import com.tothenew.ecommerce.exception.NullException;
import com.tothenew.ecommerce.exception.UserNotFoundException;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.repository.TokenRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDto customerDto;
    @Autowired
    CustomerService customerService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailVerification mailVerification;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    SellerService sellerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    SendMail sendMail;

    public String RegisterCustomer(CustomerDto customerDto) throws PasswordException {
        if(customerDto.getPassword().equals(customerDto.getConfirmPassword())){
            if(customerRepository.findByEmail(customerDto.getEmail())==null) {
                Customer customer=new Customer();
                customer = customerService.convtToCustomer(customerDto);
                customer.setActive(false);
                customer.setPassword(passwordEncoder.encode(customer.getPassword()));
                mailVerification.sendNotification(customer);
                customerRepository.save(customer);
                return customer.getEmail()+" successfully registered";
            }
            else
                throw new EmailAlreadyExistsException("Customer of this email already exist");
        }
        else
            throw new PasswordException("password and confirm password does not match");
    }

    public String resendActivationLink(String mailId) {
        User user=userRepository.findByEmail(mailId);
        if(user==null)
            throw  new UserNotFoundException("this user doesn't exist");
        else{
            for (Token token : tokenRepository.findAll())
            {
                if (token.getName().equals(user.getUsername()))
                {
                    tokenRepository.delete(token);
                }
            }
            if (user.getActive()==false)
                mailVerification.sendNotification(user);
            else
                throw new NullException("account is already active");
            return "activation token sent to the given email address";
        }
    }

    public String RegisterSeller(SellerDto sellerDto) throws PasswordException {
        if(sellerDto.getPassword().equals(sellerDto.getConfirmPassword())){
            if(sellerRepository.findByEmail(sellerDto.getEmail())== null){
                Seller seller=new Seller();
                seller.setActive(false);
                seller= sellerService.convtToSeller(sellerDto);
                seller.setPassword(passwordEncoder.encode(seller.getPassword()));
                String subject="About account Creation";
                String text="Your account of company name "+seller.getCompanyName()+" is successfully created.";
                sendMail.sendEmail(seller.getEmail(), subject, text);
                sellerRepository.save(seller);
                return seller.getEmail()+" successfully registered";
            }
            else
                throw new EmailAlreadyExistsException("Customer of this email already exist");
        }
        else
            throw new PasswordException("password and confirm password does not match");
    }
}
