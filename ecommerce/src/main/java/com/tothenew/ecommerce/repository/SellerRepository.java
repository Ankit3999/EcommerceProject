package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Integer>, PagingAndSortingRepository<Seller, Integer> {
    String findByGst(String GST);
    Long findByCompanyContact(Long COMPANY_CONTACT);
    String findByCompanyName(String COMPANY_NAME);
    Seller findByUsername(String username);
    Seller findByEmail(String email);

    @Query(value = "select name,id from category where parent_id not in (select id from category where parent_id is null)", nativeQuery = true)
    public List<Object[]> getSubcategory();
}
