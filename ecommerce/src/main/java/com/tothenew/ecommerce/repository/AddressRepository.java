package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Modifying
    @Transactional
    @Query("delete from Address where id= :id")
    void deleteAddressById(@Param("id") Long id);
}
