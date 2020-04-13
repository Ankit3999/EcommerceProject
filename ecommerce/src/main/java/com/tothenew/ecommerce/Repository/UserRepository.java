package com.tothenew.ecommerce.Repository;


import com.tothenew.ecommerce.Entity.Customer;
import com.tothenew.ecommerce.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);
    User findByEmail(String email);
}