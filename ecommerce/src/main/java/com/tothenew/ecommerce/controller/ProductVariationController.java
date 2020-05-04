package com.tothenew.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tothenew.ecommerce.dto.ProductVariationDto;
import com.tothenew.ecommerce.entity.ProductVariation;
import com.tothenew.ecommerce.services.ProductService;
import com.tothenew.ecommerce.services.ProductVariationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductVariationController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductVariationService productVariationService;


    @ApiOperation("to add a product variation")
    @PostMapping("/seller/addProductVariations/{productId}")
    public void addNewProductVariation(@Valid @RequestBody ProductVariation productVariation, @PathVariable Long productId) throws IOException {

        productVariationService.addNewProductVariation(productVariation, productId);
    }


    @ApiOperation("uri for seller to view a single product variation")
    @GetMapping("/seller/viewSingleProductVariation/{productVariationId}")
    public ProductVariationDto getSingleProductVariation(@PathVariable Long productVariationId) throws IOException {
        return productVariationService.getSingleProductVariation(productVariationId);
    }

    @ApiOperation("uri for seller to get all product variation of a product")
    @GetMapping("/seller/getAllProductVariations/{productId}")
    public List<ProductVariationDto> getAllProductVariations(@PathVariable Long productId) throws IOException {
        return productVariationService.getAllProductVariations(productId);
    }


    @ApiOperation("uri for seller to edit product Variation")
    @PostMapping("/seller/editProductVariations/{productVariationId}")
    public void updateProductVariation(@RequestBody ProductVariation productVariation, @PathVariable Long productVariationId) throws IOException {
        productVariationService.editProductVariation(productVariation,productVariationId);
    }

}
