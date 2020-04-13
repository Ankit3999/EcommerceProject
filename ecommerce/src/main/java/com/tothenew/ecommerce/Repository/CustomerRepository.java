package com.tothenew.ecommerce.Repository;

import com.tothenew.ecommerce.Entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer>, PagingAndSortingRepository<Customer, Integer> {
    String findByEmail(String email);
    Customer findByUsername(String username);
}
