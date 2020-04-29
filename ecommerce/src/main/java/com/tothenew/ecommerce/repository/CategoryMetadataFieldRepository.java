package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.CategoryMetadataField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CategoryMetadataFieldRepository extends PagingAndSortingRepository<CategoryMetadataField,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field_values where category_metadata_id=:category_metadata_id",nativeQuery = true)
    public void remove(@Param("category_metadata_id") Long category_metadata_id);

    @Modifying
    @Transactional
    @Query(value = "delete from category_metadata_field where id=:id",nativeQuery = true)
    public void deleteMetadatField(@Param("id") Long id);

}
