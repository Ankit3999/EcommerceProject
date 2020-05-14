package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Long> {

    @Query(value = "select customer_id from orders where ids=:id", nativeQuery = true)
    Long findCustomer(@Param(value = "id")Long id);
}
