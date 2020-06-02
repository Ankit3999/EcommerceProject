package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends MongoRepository<ProductReview, String> {

    @Query(value = "{productId:?0}")
    List<ProductReview> findByProduct(Long ProductId);


    List<ProductReview> findByCustomerIdGreaterThan(Long CustomerId);
}
