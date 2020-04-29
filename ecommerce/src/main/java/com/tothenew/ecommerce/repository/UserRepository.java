package com.tothenew.ecommerce.repository;


import com.tothenew.ecommerce.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long>, PagingAndSortingRepository<User,Long> {

    User findByUsername(String username);
    User findByEmail(String email);

    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role='ROLE_CUSTOMER'))",nativeQuery = true)
    List<Long> findIdOfCustomers(Pageable pageable);


    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role='ROLE_SELLER'))",nativeQuery = true)
    List<Long> findIdOfSellers(Pageable pageable);
}