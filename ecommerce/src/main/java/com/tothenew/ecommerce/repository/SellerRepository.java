package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SellerRepository extends CrudRepository<Seller, Integer>, PagingAndSortingRepository<Seller, Integer> {
    String findByGst(String GST);
    Long findByCompanyContact(Long COMPANY_CONTACT);
    String findByCompanyName(String COMPANY_NAME);
    Seller findByUsername(String username);
    Seller findByEmail(String email);
}
