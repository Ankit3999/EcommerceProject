package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.OrderProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    @Query(value = "select order_id from order_product where product_variation_id= :id", nativeQuery = true)
    Long getOrderId(@Param(value = "id") Long id);
}
