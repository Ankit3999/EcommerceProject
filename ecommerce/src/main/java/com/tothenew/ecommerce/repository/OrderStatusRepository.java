package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long> {
}
