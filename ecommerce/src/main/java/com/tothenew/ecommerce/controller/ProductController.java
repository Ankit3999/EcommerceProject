package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ProductService productService;

    @PostMapping("/seller/product")
    String addProduct(@RequestBody ProductDto productDto){
        String email=currentUserService.getUser();
        return productService.addProduct(email, productDto);
    }

    @GetMapping("/seller/product/{id}")
    public Object getProductForSeller(@PathVariable Long id){
        String email=currentUserService.getUser();
        return productService.getProduct(id, email);
    }

    @DeleteMapping("/seller/product/{id}")
    public String  deleteProductById(@PathVariable Long id, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        return productService.deleteProductById(id, email);
    }

    @PatchMapping("/seller/product/{pId}")
    public String updateProductById(@PathVariable Long pId, @RequestBody ProductDto productDto){
        String email=currentUserService.getUser();
        return productService.updateProductByProductId(pId, email, productDto);
    }

    @PutMapping("/product/activate/{id}")
    public String activateProduct(@PathVariable Long id){
        return productService.activateProduct(id);
    }

    @PutMapping("/product/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id){
        return productService.deactivateProductById(id);
    }
}