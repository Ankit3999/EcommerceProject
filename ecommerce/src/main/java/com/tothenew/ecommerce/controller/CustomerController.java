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

    @ApiOperation(value = "homepage for customer")
    @GetMapping("/customer/home")
    ResponseEntity customerhome(){
        String msg="customer home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "to check currently logged in customer's profile")
    @GetMapping("/customer/profile")
    CustomerProfileDto viewprofile(HttpServletRequest request){ return customerService.viewProfile(); }

    @ApiOperation(value = "to view currently logged in customer's profile image")
    @GetMapping("/customer/viewProfileImage")
    public ResponseEntity<Object> viewProfileImage(HttpServletRequest request) throws IOException {
        String username = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(username);
        String filename = customer.getId().toString()+"_";
        return imageService.downloadImage(filename,request);
    }

    @ApiOperation(value = "to upload a pic for currently logged in customer")
    @PostMapping("/customer/uploadProfilePic")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException
    {
        String username = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(username);
        return imageService.uploadSingleImage(file,customer);
    }

    @ApiOperation(value = "to update currently logged in customer's profile")
    @PatchMapping("/customer/profile/update")
    void updateprofile(@RequestBody CustomerDto customerDto){
        customerService.updateProfile(customerDto);
    }

    @ApiOperation(value = "to view currently logged in customer's address")
    @GetMapping("/customer/address")
    List<AddressDto> viewAddress(){
        return userService.getAddress();
    }

    @ApiOperation(value = "to add address for currently logged in customer")
    @PostMapping("/customer/address/add")
    String addAddress(@Valid @RequestBody AddressDto addressDto){
        return userService.addAddress(addressDto);
    }

    @ApiOperation(value = "to delete an address for currently logged in customer")
    @DeleteMapping("/customer/address/delete")
    String deleteAddress(@RequestParam("id") Long id){
        return userService.deleteAddress(id);
    }

    @ApiOperation(value = "to update an address for currently logged in customer")
    @PutMapping("/customer/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        userService.updateAddress(id, addressDto);
        return "Address with id "+id+" updated successfully";
    }

    @ApiOperation(value = "to update password for currently logged in customer")
    @PatchMapping("/customer/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        return userService.updatePassword(newPassword);
    }

}
