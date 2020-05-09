package com.tothenew.ecommerce.RedisRepository;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface ProductRedisRepository {



    public void save(Product product);

    public Map<String, Product> findAll();

    public Product findById(Long id);

    public void delete(Long id);
}
