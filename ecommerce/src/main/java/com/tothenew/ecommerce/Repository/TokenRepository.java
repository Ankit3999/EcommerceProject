package com.tothenew.ecommerce.Repository;

import com.tothenew.ecommerce.Entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Integer> {
}
