package com.tothenew.ecommerce.RedisController;

import com.tothenew.ecommerce.RedisRepository.ProductRedisRepository;
import com.tothenew.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProductRedisController {
    @Autowired
    ProductRedisRepository productRedisRepository;
    @GetMapping("/product/all")
    public Map<String, Product> findAll(){
        return productRedisRepository.findAll();
    }

    @GetMapping("/product/one/{id}")
    public Product findOne(@PathVariable("id") Long id){
        return productRedisRepository.findById(id);
    }
}
