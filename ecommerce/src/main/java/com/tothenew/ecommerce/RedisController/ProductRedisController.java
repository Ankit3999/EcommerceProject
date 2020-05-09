package com.tothenew.ecommerce.RedisController;

import com.tothenew.ecommerce.RedisRepository.ProductRedisRepository;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ProductRedisController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    ProductRedisRepository productRedisRepository;
    @Autowired
    ProductRepository productRepository;
    
    @GetMapping("/product/all")
    public Map<String, Product> findAll(){
        return productRedisRepository.findAll();
    }

    @GetMapping("/product/one/{id}")
    public Product findOne(@PathVariable("id") Long id){
        return productRedisRepository.findById(id);
    }


    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Product getUser(@PathVariable Integer productId) {
        LOG.info("Getting user with ID {}.", productId);
        return productRepository.findOne(Long.valueOf(productId));
        //return userRepository.findOne(Long.valueOf(userId));
    }
}
