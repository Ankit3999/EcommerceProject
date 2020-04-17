package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.CustomerService;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    UserRepository userRepository;

    @GetMapping("/customer/home")
    ResponseEntity customerhome(){
        List<Product> productList=new ArrayList<>();
        productList= (List<Product>) productRepository.findAll();
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    CustomerProfileDto viewprofile(HttpServletRequest request){
        String username=currentUserService.getUser();
        //System.out.println("username is "+username);
        Customer customer= (Customer) userRepository.findByEmail(username);
        return customerService.toCustomerViewProfileDto(customer);
        //return username;
    }

    @PatchMapping("/customer/profile/update")
    void updateprofile(@RequestBody CustomerDto customerDto){
        userService.updateProfile(customerDto);
    }

    @GetMapping("/customer/address")
    Set<Address> viewAddress(){
        return userService.getAddress(currentUserService.getUser());
    }

    @PostMapping("/customer/address/add")
    String addAddress(@Valid @RequestBody AddressDto addressDto){
        String username=currentUserService.getUser();
        Customer customer=(Customer) customerRepository.findByEmail(username);
        return customerService.addAddress(customer, addressDto);
    }

    @DeleteMapping("/customer/address/delete")
    String passwordDelete(@RequestParam("id") Long id){
        String username=currentUserService.getUser();
        return customerService.deleteAddress(id, username);
    }

    @PutMapping("/customer/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        String username=currentUserService.getUser();
        return customerService.updateAddress(id, addressDto, username);
    }

    @PatchMapping("/customer/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        String username=currentUserService.getUser();
        return customerService.updatePassword(username, newPassword);
    }
}
