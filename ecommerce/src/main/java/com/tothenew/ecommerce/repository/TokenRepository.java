package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
}
