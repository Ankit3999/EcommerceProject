package com.tothenew.ecommerce.RedisRepository;

import com.tothenew.ecommerce.entity.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class ProductRedisRepository {
    private RedisTemplate<String, Product> redisTemplate;
    private HashOperations hashOperations;

    public ProductRedisRepository(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations=redisTemplate.opsForHash();
    }


    public void save(Product product) {
        hashOperations.put("product", product.getId(), product);
    }

    public Map<String, Product> findAll() {
        return hashOperations.entries("product");
    }

    public Product findById(Long id) {
        return (Product) hashOperations.get("product", id);
    }


    public void delete(Long id){
        hashOperations.delete("product", id);
    }
}
