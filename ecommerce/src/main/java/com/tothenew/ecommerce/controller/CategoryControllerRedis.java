package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.entity.Category;
import com.tothenew.ecommerce.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category/redis/")
public class CategoryControllerRedis {
    @Autowired
    CategoryRepository categoryRepository;

    //using redis

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Cacheable(value = "category", key = "#categoryId", unless = "#result==null")
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public Optional<Category> getCategory(@PathVariable("categoryId") Long categoryId){
        LOG.info("getting product id " +categoryId);
        return categoryRepository.findById(categoryId);
    }


    @CachePut(value = "category", key = "#category.id")
    @PutMapping("/update")
    public Category updatePersonByID(@RequestBody Category category) {
        categoryRepository.save(category);
        return category;
    }


    @CacheEvict(value = "category", allEntries=true)
    @DeleteMapping("/delete/{id}")
    public void deleteUserByID(@PathVariable Long id) {
        LOG.info("deleting category with id {}", +id);
        categoryRepository.deleteById(id);
    }

}
