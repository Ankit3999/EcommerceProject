package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.dto.ViewProductDto;
import com.tothenew.ecommerce.dto.ViewProductDtoforCustomer;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.ProductService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ProductService productService;

    //done
    //for admin

    @ApiOperation("to view a single product")
    @GetMapping("/admin/product/{productId}")
    public ViewProductDto getOneProductForAdmin(@PathVariable Long productId) throws NotFoundException {
        return productService.viewSingleProductForAdmin(productId);
    }

    @ApiOperation("to show all the active products")
    @GetMapping("/admin/allProducts")
    public List<ViewProductDtoforCustomer> allProductsForAdmin(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetailsForAdmin(pageNo, pageSize, sortBy);
    }

    @ApiOperation("to activate a product")
    @GetMapping("/admin/product/activate/{id}")
    public String activateProduct(@PathVariable Long id){
        return productService.activateProduct(id);
    }

    @ApiOperation("To deactivate a product")
    @GetMapping("/admin/product/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id){
        return productService.deactivateProductById(id);
    }



    //done
    //for customer

    @ApiOperation("to view a single product")
    @GetMapping("/customer/product/{productId}")
    public ViewProductDto getProductForCustomer(@PathVariable Long productId) throws NotFoundException {
        return productService.viewSingleProductForCustomer(productId);
    }

    @ApiOperation("to view all the active products of a particular category")
    @GetMapping("/customer/allProduct/{categoryId}")
    public List<ViewProductDtoforCustomer> allProductsForCustomer(@PathVariable Long categoryId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetailsForCustomer(categoryId, pageNo, pageSize, sortBy);
    }

    @ApiOperation("to view similar products based on category and brand")
    @GetMapping("/customer/similar-products/{productId}")
    public List<ViewProductDtoforCustomer> getSimilarProductsByProductIdForCustomer(@PathVariable Long productId, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sortByField) throws NotFoundException {
        return productService.getSimilarProducts(productId, offset, size, sortByField);
    }



    //seller

    @ApiOperation("to add a product")
    @PostMapping("/seller/addProduct/{category}")
    String addProduct(@RequestBody Product product, @PathVariable(name = "category") Long category) throws NotFoundException {
        return productService.addProduct(product, category);
    }

    @ApiOperation("to get a single product")
    @GetMapping("/seller/product/{id}")
    public ProductDto getProduct(@PathVariable Long id) throws NotFoundException {
        return productService.getProduct(id);
    }

    @ApiOperation("to delete a product")
    @DeleteMapping("/seller/product/{id}")
    public String  deleteProductById(@PathVariable Long id, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        return productService.deleteProductById(id, email);
    }

    @ApiOperation("to update a product")
    @PatchMapping("/seller/product/{pId}")
    public String updateProductById(@PathVariable Long pId, @RequestBody Product product) throws NotFoundException {
        String email=currentUserService.getUser();
        productService.updateProductByProductId(pId, email, product);
        return "Product is successfully updated";
    }

    @ApiOperation("to get all the products")
    @GetMapping("/seller/allProducts")
    public List<ViewProductDto> allProducts(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                            @RequestParam(name = "pazeSize",defaultValue = "10") Integer pageSize,
                                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) throws NotFoundException {
        return productService.getProductDetails(pageNo, pageSize, sortBy);
    }


}