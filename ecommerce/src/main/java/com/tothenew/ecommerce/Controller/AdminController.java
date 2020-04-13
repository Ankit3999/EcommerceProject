package com.tothenew.ecommerce.Controller;

import com.tothenew.ecommerce.DtOs.CustomerDto;
import com.tothenew.ecommerce.DtOs.SellerDto;
import com.tothenew.ecommerce.Entity.Customer;
import com.tothenew.ecommerce.Entity.Seller;
import com.tothenew.ecommerce.Repository.CustomerRepository;
import com.tothenew.ecommerce.Repository.SellerRepository;
import com.tothenew.ecommerce.Services.UserService;
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
    CustomerRepository customerRepository;
    @Autowired
    ResponseEntity responseEntity;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    Customer customer;
    @Autowired
    Seller seller;

    @Autowired
    CustomerDto customerDto;
    @Autowired
    SellerDto sellerDto;
    @Autowired
    UserService userService;

    @GetMapping("/admin/home")
    public ResponseEntity adminHome(){
        String msg= "Admin home";
        responseEntity= new ResponseEntity(msg, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/all/customer")
    public Page<Customer> showcustomer(@RequestParam("field") String field){
        PageRequest pageable=  PageRequest.of(0,10, Sort.by(field));
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/all/seller")
    public void showseller(@RequestParam("field") String field){
        PageRequest pageable=  PageRequest.of(0,10, Sort.by(field));
        sellerRepository.findAll(pageable);
    }

    @GetMapping("/activate/customer")
    public void activateCustomer(@RequestParam("id") Long id){
        userService.activateUser(id);
    }

    @GetMapping("/deactivate/customer")
    public void deactivateCustomer(@RequestParam("id") Long id){
        userService.deActivateuser(id);
    }

}
