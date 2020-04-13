package com.tothenew.ecommerce.Repository;

import com.tothenew.ecommerce.Entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
