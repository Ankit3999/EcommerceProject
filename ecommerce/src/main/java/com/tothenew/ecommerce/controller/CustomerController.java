package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
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
import java.util.List;

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
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    CustomerProfileDto viewprofile(HttpServletRequest request){ return customerService.viewProfile(); }

    @PatchMapping("/customer/profile/update")
    void updateprofile(@RequestBody CustomerDto customerDto){
        customerService.updateProfile(customerDto);
    }

    @GetMapping("/customer/address")
    List<AddressDto> viewAddress(){
        return userService.getAddress();
    }

    @PostMapping("/customer/address/add")
    String addAddress(@Valid @RequestBody AddressDto addressDto){
        return userService.addAddress(addressDto);
    }

    @DeleteMapping("/customer/address/delete")
    String passwordDelete(@RequestParam("id") Long id){
        return userService.deleteAddress(id);
    }

    @PutMapping("/customer/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        userService.updateAddress(id, addressDto);
        return "Address with id "+id+" updated successfully";
    }

    @PatchMapping("/customer/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        return userService.updatePassword(newPassword);
    }
}
