package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.entity.ProductReview;
import com.tothenew.ecommerce.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/")
public class ProductReviewController {
    @Autowired
    ProductReviewRepository productReviewRepository;

    @PostMapping("save")
    String save(@RequestBody ProductReview productReview){
        productReviewRepository.save(productReview);
        return productReview.getReview()+" is your review";
    }

    @GetMapping("getAll")
    List<ProductReview> getall(){
       return productReviewRepository.findAll();
    }

    @GetMapping("byProduct/{id}")
    List<ProductReview> byProductId(@PathVariable("id") Long id){
        return productReviewRepository.findByProduct(id);
    }

    @GetMapping("sorting/{id}")
    List<ProductReview> byids(@PathVariable("id") Long id){
        return productReviewRepository.findByCustomerIdGreaterThan(id);
    }
}
