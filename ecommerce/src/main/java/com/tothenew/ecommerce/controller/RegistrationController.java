package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dao.CustomerImageDao;
import com.tothenew.ecommerce.dao.SellerImageDao;
import com.tothenew.ecommerce.services.TokenService;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.SellerDto;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.CustomerService;
import com.tothenew.ecommerce.services.RegistrationService;
import com.tothenew.ecommerce.services.SellerService;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    SellerService sellerService;

    @Autowired
    CustomerImageDao customerImageDao;
    @Autowired
    SellerImageDao sellerImageDao;
    @Autowired
    RegistrationService registrationService;

    @Autowired
    MailVerification mailVerification;

    @Autowired
    TokenService tokenService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation("Uri for customer registration")
    @PostMapping("/register/customer")
    String saveCustomer(@Valid @RequestBody CustomerDto customerDto) throws PasswordException {
        return registrationService.RegisterCustomer(customerDto);
    }

    /*  @PostMapping("/register/upload/image")
    ResponseEntity<Object> uploadImage(@Valid MultipartFile file, Customer customer) throws IOException {
        return customerImageDao.uploadSingleImage(file, customer);
    }*/

    @ApiOperation("uri for activating user after which user can login into application")
    @PutMapping("/verify/customer")
    String verifyCustomer(@RequestParam("token") String token){
        tokenService.verifyToken(token);
        return "user activated successfully";
    }

    @ApiOperation("uri for resending activation token if last one is expired")
    @PutMapping("/resendtoken")
    String reVerifyUser(@RequestParam("mailId") String mailId){
        return registrationService.resendActivationLink(mailId);
    }

    @ApiOperation("Uri for seller registration")
    @PostMapping("/register/seller")
    String saveSeller(@Valid @RequestBody SellerDto sellerDto) throws PasswordException {
        return registrationService.RegisterSeller(sellerDto);
    }

    /*@PostMapping("/register/upload/image")
    ResponseEntity<Object> uploadImage(@Valid MultipartFile file, Seller seller) throws IOException {
        return sellerImageDao.uploadSingleImage(file, seller);
    }*/
}
