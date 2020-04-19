package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.UserAttempts;
import org.springframework.data.repository.CrudRepository;

public interface UserAttemptsRepository extends CrudRepository<UserAttempts, Long> {
}
