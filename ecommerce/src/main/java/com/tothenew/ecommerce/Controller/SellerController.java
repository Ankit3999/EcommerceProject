package com.tothenew.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {

    @Autowired
    ResponseEntity responseEntity;

    @GetMapping("seller/home")
    ResponseEntity sellerhome(){
        String msg="Seller home";
        responseEntity=new ResponseEntity(msg, HttpStatus.OK);
        return responseEntity;
    }
}
