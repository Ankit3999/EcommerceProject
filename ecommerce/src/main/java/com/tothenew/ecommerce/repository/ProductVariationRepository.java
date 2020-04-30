package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {
    @Query(value = "select product_id from product_variation where id =:id",nativeQuery = true)
    public Long getProductId(@Param(value = "id") Long id);}
