package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.dto.ViewProductDto;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ProductService productService;

    //seller
    @PostMapping("/seller/product")
    String addProduct(@RequestBody ProductDto productDto){
        String email=currentUserService.getUser();
        return productService.addProduct(email, productDto);
    }

    @GetMapping("/seller/product/{id}")
    public Object getProduct(@PathVariable Long id){
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

    @GetMapping("seller/product/{productId}")
    public ViewProductDto getOneProductsByProductIdForSeller(@PathVariable Long productId) throws NotFoundException {
        return productService.viewSingleProductForSeller(productId);
    }

    @GetMapping("/seller/allProducts")
    public List<ViewProductDto> allProducts(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetails(pageNo, pageSize, sortBy);
    }

    @GetMapping("/seller/getProductVariation")
    public void getProductVariation(){

    }

    //productVariation apis for seller


    //for admin
    @PutMapping("/admin/product/{productId}")
    public ViewProductDto getOneProductForAdmin(@PathVariable Long productId) throws NotFoundException {
        return productService.viewSingleProductForAdmin(productId);
    }

    @GetMapping("/admin/allProducts")
    public List<ViewProductDto> allProductsForAdmin(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetailsForAdmin(pageNo, pageSize, sortBy);
    }

    @PutMapping("/admin/product/activate/{id}")
    public String activateProduct(@PathVariable Long id){
        return productService.activateProduct(id);
    }

    @PutMapping("/admin/product/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id){
        return productService.deactivateProductById(id);
    }

    //for customer
    @GetMapping("/customer/product/{productId}")
    public ViewProductDto getProductForCustomer(@PathVariable Long productId) throws NotFoundException {
        return productService.viewSingleProductForCustomer(productId);
    }

    @GetMapping("customer/allProduct/{categoryId}")
    public List<ViewProductDto> allProductsForCustomer(@PathVariable Long categoryId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetailsForCustomer(categoryId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/customer/similar-products/{productId}")
    public List<ViewProductDto> getSimilarProductsByProductIdForCustomer(@PathVariable Long productId, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sortByField) throws NotFoundException {
        return productService.getSimilarProducts(productId, offset, size, sortByField);
    }
}