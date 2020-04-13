package com.tothenew.ecommerce.Controller;

import com.tothenew.ecommerce.Entity.Product;
import com.tothenew.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ResponseEntity responseEntity;

    @GetMapping("/customer/home")
    String customerhome(){
        List<Product> productList=new ArrayList<>();
        productList= (List<Product>) productRepository.findAll();
        String msg="customer home";
        responseEntity=new ResponseEntity(productList, HttpStatus.OK);
        return "Customer home" +responseEntity;
    }
}
