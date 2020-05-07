package com.tothenew.ecommerce.Criteria;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.User;

public interface Criteria {

    User findById(Long id);

    Customer findByEmail(String email);
}
