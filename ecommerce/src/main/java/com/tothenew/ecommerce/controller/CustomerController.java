package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.CustomerService;
import com.tothenew.ecommerce.services.ImageService;
import com.tothenew.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {
    //All working

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
    @Autowired
    ImageService imageService;

    @GetMapping("/customer/home")
    ResponseEntity customerhome(){
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    CustomerProfileDto viewprofile(HttpServletRequest request){ return customerService.viewProfile(); }

    @GetMapping("/customer/viewProfileImage")
    public ResponseEntity<Object> viewProfileImage(HttpServletRequest request) throws IOException {
        String username = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(username);
        String filename = customer.getId().toString()+"_";
        return imageService.downloadImage(filename,request);
    }

    @PostMapping("/customer/uploadProfilePic")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException
    {
        String username = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(username);
        return imageService.uploadSingleImage(file,customer);
    }

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
