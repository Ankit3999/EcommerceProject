package com.tothenew.ecommerce.Repository;

import com.tothenew.ecommerce.Entity.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SellerRepository extends CrudRepository<Seller, Integer>, PagingAndSortingRepository<Seller, Integer> {
    Long findByGst(Long GST);
    Long findByCompanyContact(Long COMPANY_CONTACT);
    String findByCompanyName(String COMPANY_NAME);
}
