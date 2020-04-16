package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.CustomerService;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class CustomerController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CustomerDto customerDto;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;

    @GetMapping("/customer/home")
    ResponseEntity customerhome(){
        List<Product> productList=new ArrayList<>();
        productList= (List<Product>) productRepository.findAll();
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    CustomerProfileDto viewprofile(){
        String username=currentUserService.getUser();
        Customer customer=customerRepository.findByUsername(username);
        return customerService.toCustomerViewProfileDto(customer);
    }

    @GetMapping("/customer/profile/update")
    void updateprofile(@RequestBody CustomerDto customerDto){
        userService.updateProfile(customerDto);
    }

    @GetMapping("/customer/address")
    Set<Address> viewAddress(){
        return userService.getAddress(currentUserService.getUser());
    }

    @GetMapping("/customer/address/add")
    String addAddress(@Valid @RequestBody AddressDto addressDto){
        String username=currentUserService.getUser();
        Customer customer=customerRepository.findByUsername(username);
        return customerService.addAddress(customer, addressDto);
    }

    @GetMapping("/customer/address/delete")
    String passwordDelete(@RequestParam("id") Long id){
        String username=currentUserService.getUser();
        return customerService.deleteAddress(id, username);
    }

    @GetMapping("/customer/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        String username=currentUserService.getUser();
        return customerService.updateAddress(id, addressDto, username);
    }

    @GetMapping("/customer/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        String username=currentUserService.getUser();
        return customerService.updatePassword(username, newPassword);
    }
}
