package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.services.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    @Autowired
    OrderService orderService;

    @ApiOperation("to place order")
    @GetMapping("/customer/placeOrder/{productVariationId}/{quantity}/{paymentMethod}/{AddressId}")
    public void placeOrder(@PathVariable Long productVariationId, @PathVariable int quantity,
                           @PathVariable String paymentMethod, @PathVariable Long AddressId){
        orderService.placeOrder(productVariationId, quantity, paymentMethod, AddressId);
    }

    @ApiOperation("to confirm order from seller")
    @GetMapping("/seller/orders")
    public void getOrders(){
        orderService.toSellerMessage();
    }

}
