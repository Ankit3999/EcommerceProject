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
import java.util.List;

@RestController
public class ProductVariationController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductVariationService productVariationService;


    @ApiOperation("uri for seller to add a product variation")
    @PostMapping("/addProductVariations/{productId}")
    public void addNewProductVariation(@Valid @RequestBody ProductVariation productVariation, @PathVariable Long productId) throws JsonProcessingException {

        productVariationService.addNewProductVariation(productVariation, productId);
    }


    @ApiOperation("uri for seller to view a single product variation")
    @GetMapping("/viewSingleProductVariation/{productVariationId}")
    public ProductVariationDto getSingleProductVariation(@PathVariable Long productVariationId) throws JsonProcessingException {
        return productVariationService.getSingleProductVariation(productVariationId);
    }

    @ApiOperation("uri for seller to get all product variation of a product")
    @GetMapping("/getAllProductVariations/{productId}")
    public List<ProductVariationDto> getAllProductVariations(@PathVariable Long productId) throws JsonProcessingException {
        return productVariationService.getAllProductVariations(productId);
    }


    @ApiOperation("uri for seller to edit product Variation")
    @PostMapping("/editProductVariations/{productVariationId}")
    public void updateProductVariation(@RequestBody ProductVariation productVariation, @PathVariable Long productVariationId) throws JsonProcessingException {
        productVariationService.editProductVariation(productVariation,productVariationId);
    }

}
