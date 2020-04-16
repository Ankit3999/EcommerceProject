package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer>, PagingAndSortingRepository<Customer, Integer> {
    String findByEmail(String email);
    Customer findByUsername(String username);
}
