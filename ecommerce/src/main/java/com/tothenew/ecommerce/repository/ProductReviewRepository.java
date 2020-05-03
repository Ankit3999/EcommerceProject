package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
}
