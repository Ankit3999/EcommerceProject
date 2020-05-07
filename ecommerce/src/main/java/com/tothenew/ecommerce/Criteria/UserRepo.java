package com.tothenew.ecommerce.Criteria;

import com.tothenew.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<Long, User>, JpaSpecificationExecutor<User> {
    Optional<User> findById(Long id);
}

