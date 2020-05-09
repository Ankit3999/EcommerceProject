package com.tothenew.ecommerce.RedisRepository;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ProductRedisRepositoryImpl implements ProductRedisRepository{
    private RedisTemplate<String, Product> redisTemplate;
    private HashOperations hashOperations;

    public ProductRedisRepositoryImpl(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations=redisTemplate.opsForHash();
    }

    @Override
    public void save(Product product) {
        hashOperations.put("product", product.getId(), product);
    }

    @Override
    public Map<String, Product> findAll() {
        return hashOperations.entries("customer");
    }

    @Override
    public Product findById(Long id) {
        return (Product) hashOperations.get("product", id);
    }

    @Override
    public void delete(Long id){
        hashOperations.delete("product", id);
    }
}
