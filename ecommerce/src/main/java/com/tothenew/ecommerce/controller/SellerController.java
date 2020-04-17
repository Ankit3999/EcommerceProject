package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.*;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.SellerService;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class SellerController {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CustomerDto customerDto;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;
    @Autowired
    SellerService sellerService;

    @GetMapping("/seller/home")
    ResponseEntity sellerHome(){
        String msg="Seller home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/seller/profile")
    SellerProfileDto viewProfile(){
        System.out.println("welcome");
        String username=currentUserService.getUser();
        System.out.println("----------------"+username);
        Seller seller =(Seller) sellerRepository.findByEmail(username);
        return sellerService.toSellerViewProfileDto(seller);
    }

    @PatchMapping("/seller/profile/update")
    String updateprofile(@RequestBody SellerDto sellerDto){
        return sellerService.updateProfile(sellerDto);
    }

    @PatchMapping("/seller/password/update")
    String updatePassword(@RequestParam("password") String newPassword){
        String username=currentUserService.getUser();
        return sellerService.updatePassword(username, newPassword);
    }

    @PutMapping("/seller/address/update/{id}")
    String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id){
        String username=currentUserService.getUser();
        return sellerService.updateAddress(id, addressDto, username);
    }
}
