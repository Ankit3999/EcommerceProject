package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>, PagingAndSortingRepository<Customer, Integer> {
    Customer findByEmail(String email);
    Customer findByUsername(String username);
}
