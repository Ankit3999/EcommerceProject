package com.tothenew.ecommerce.repository;

import com.tothenew.ecommerce.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query(value = "select exists(select * from category where parent_id=:parent_id)",nativeQuery = true)
    int checkIfLeaf(@Param("parent_id") Long parent_id);

    @Query(value = "select id from category where parent_id=:parent_id",nativeQuery = true)
    List<Long> getIdsOfSubcategories(@Param("parent_id") Long parent_id);

    @Query(value = "select id from category",nativeQuery = true)
    List<Long> getIdsOfCategory(Pageable pageable);

    @Query(value = "select id from category where name=:name",nativeQuery = true)
    Long getIdOfParentCategory(@Param("name") String name);

    @Query(value = "select name from category where parent_id is null",nativeQuery = true)
    List<Object[]> getMainCategory();

    @Query(value = "select name from category where parent_id in(select id from category where id=:id)",nativeQuery = true)
    List<Object[]> getSubCategoryOfCategory(@Param("id") Long id);
}
