package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.UserAttempts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttemptsRepository extends CrudRepository<UserAttempts, Long> {
}
