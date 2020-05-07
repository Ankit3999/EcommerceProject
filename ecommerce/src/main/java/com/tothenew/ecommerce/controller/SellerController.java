package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.*;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.ImageService;
import com.tothenew.ecommerce.services.SellerService;
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

@RestController
public class SellerController {
    //All working


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CustomerDto customerDto;

    @Autowired
    ImageService imageService;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;
    @Autowired
    SellerService sellerService;

    @ApiOperation(value = "homepage for seller")
    @GetMapping("/seller/home")
    ResponseEntity sellerHome(){
        String msg="Seller home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "to check currently logged in seller's profile")
    @GetMapping("/seller/profile")
    SellerProfileDto viewProfile(){ return sellerService.viewProfile(); }

    @ApiOperation(value = "to update currently logged in seller's profile")
    @PatchMapping("/seller/profile/update")
    ResponseEntity updateprofile(@RequestBody SellerDto sellerDto){
        return sellerService.updateProfile(sellerDto);
    }

    @ApiOperation(value = "to update password for currently logged in seller")
    @PatchMapping("/seller/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        return userService.updatePassword(newPassword);
    }

    @ApiOperation(value = "to update an address for currently logged in seller")
    @PutMapping("/seller/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        userService.updateAddress(id, addressDto);
        return "Address with id "+id+" updated successfully";
    }
}
