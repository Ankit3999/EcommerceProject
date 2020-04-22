package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dao.CustomerImageDao;
import com.tothenew.ecommerce.dao.SellerImageDao;
import com.tothenew.ecommerce.dao.TokenDao;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.SellerDto;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.exception.EmailAlreadyExistsException;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.CustomerService;
import com.tothenew.ecommerce.services.SellerService;
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
    MailVerification mailVerification;

    @Autowired
    TokenDao tokenDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register/customer")
    String saveCustomer(@Valid @RequestBody CustomerDto customerDto){
        if(customerRepository.findByEmail(customerDto.getEmail())==null) {
            Customer customer=new Customer();
            customer = customerService.convtToCustomer(customerDto);
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            mailVerification.sendNotificaitoin(customer);
            customerRepository.save(customer);
            return "Success";
        }
        else
            throw new EmailAlreadyExistsException("Customer of this email already exist");

    }
  /*  @PostMapping("/register/upload/image")
    ResponseEntity<Object> uploadImage(@Valid MultipartFile file, Customer customer) throws IOException {
        return customerImageDao.uploadSingleImage(file, customer);
    }*/

    @PutMapping("/verify/customer")
    String verifycustomer(@RequestParam("token") String toke){
        tokenDao.verifyToken(toke);
        return "success";

    }

    @PostMapping("/register/seller")
    String saveSeller(@Valid @RequestBody SellerDto sellerDto){
        if(sellerRepository.findByGst(sellerDto.getGst())== null || sellerRepository.findByCompanyContact(sellerDto.getCompanyContact())== null || sellerRepository.findByCompanyName(sellerDto.getCompanyName())== null){
            Seller seller=new Seller();
            seller= sellerService.convtToSeller(sellerDto);
            seller.setPassword(passwordEncoder.encode(seller.getPassword()));
            mailVerification.sendNotificaitoin(seller);
            sellerRepository.save(seller);
            return "Success";
        }

        else
            return "not saved";
    }

    /*@PostMapping("/register/upload/image")
    ResponseEntity<Object> uploadImage(@Valid MultipartFile file, Seller seller) throws IOException {
        return sellerImageDao.uploadSingleImage(file, seller);
    }*/

    @PutMapping("/verify/seller")
    void verifyseller(@RequestParam("token") String toke){
        tokenDao.verifyToken(toke);

    }
}
