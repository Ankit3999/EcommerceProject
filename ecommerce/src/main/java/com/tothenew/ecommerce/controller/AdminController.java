package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/home")
    public ResponseEntity adminHome(){
        String msg= "Admin home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/admin/customer")
    public Page<Customer> showcustomer(@RequestParam("field") String field){
        PageRequest pageable=  PageRequest.of(0,10, Sort.by(field));
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/admin/seller")
    public void showseller(@RequestParam("field") String field){
        PageRequest pageable=  PageRequest.of(0,10, Sort.by(field));
        sellerRepository.findAll(pageable);
    }

    @GetMapping("/admin/activate")
    public void activateCustomer(@RequestParam("id") Long id){
        userService.activateUser(id);
    }

    @GetMapping("/admin/deactivate")
    public void deactivateCustomer(@RequestParam("id") Long id){
        userService.deActivateuser(id);
    }

}
