package com.tothenew.ecommerce.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    MailVerification mailVerification;

    @Autowired
    TokenDao tokenDao;


    @GetMapping("/customer/register")
    String saveCustomer(@Valid @RequestBody CustomerDto customerDto){
        if(customerRepository.findByEmail(customerDto.getEmail())==null) {
            Customer customer=new Customer();
            customer = customerService.convtToCustomer(customerDto);
            mailVerification.sendNotificaitoin(customer);
            customerRepository.save(customer);
            return "Success";
        }
        else
            throw new EmailAlreadyExistsException("Customer of this email already exist");

    }

    @GetMapping("/customer/verify")
    void verifycustomer(@RequestParam("token") String toke){
        tokenDao.verifyToken(toke);

    }

    @GetMapping("/seller/register")
    String saveSeller(@Valid @RequestBody SellerDto sellerDto){
        Seller seller=new Seller();
        if(sellerRepository.findByGst(sellerDto.getGst())== null || sellerRepository.findByCompanyContact(sellerDto.getCompanyContact())== null || sellerRepository.findByCompanyName(sellerDto.getCompanyName())== null){
            seller= sellerService.convtToSeller(sellerDto);
            sellerRepository.save(seller);
            return "Success";
        }

        else
            return "not saved";
    }

    @GetMapping("/seller/verify")
    void verifyseller(@RequestParam("token") String toke){
        tokenDao.verifyToken(toke);

    }
}
