package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.CategoryFilterDto;
import com.tothenew.ecommerce.dto.ViewCategoryDto;
import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.exception.NotFoundException;
import com.tothenew.ecommerce.exception.NullException;
import com.tothenew.ecommerce.repository.CategoryMetadataFieldRepository;
import com.tothenew.ecommerce.repository.CategoryMetadataFieldValuesRepository;
import com.tothenew.ecommerce.repository.CategoryRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    Long[] l={};
    @Autowired
    MessageSource messageSource;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SellerRepository sellerRepository;

    public void addCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        categoryMetadataFieldRepository.save(categoryMetadataField);
    }


    public List<CategoryMetadataField> viewCategoryMetadataField(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        if(categoryMetadataFieldRepository.findAll(paging).isEmpty()){
            throw new NotFoundException(messageSource.getMessage("message1.txt", l, LocaleContextHolder.getLocale()));
        }
        else{
            Page<CategoryMetadataField> categoryMetadataFieldPage=categoryMetadataFieldRepository.findAll(paging);
            if(categoryMetadataFieldPage.hasContent())
                return categoryMetadataFieldPage.getContent();
            else
                throw new NotFoundException(messageSource.getMessage("message2.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public void addNewSubCategory(Long parent_category_id, Category category) {
        Long[] l = {};
        int result = categoryRepository.checkIfLeaf(parent_category_id);
        if (result == 1) {
            Optional<Category> category1 = categoryRepository.findById(parent_category_id);
            if (category1.isPresent()) {
                category.setParentCategory(category1.get());
                categoryRepository.save(category);
            } else {
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NullPointerException("parent category you selected is already a leaf node");
        }
    }

    public ResponseEntity addMainCategory(Category category) {
        Long[] l = {};
        categoryRepository.save(category);
        return ResponseEntity.ok().body(messageSource.getMessage("success.txt",l, LocaleContextHolder.getLocale()));
    }

    public List<ViewCategoryDto> viewACategory(Long category_id) {
        Optional<Category> category = categoryRepository.findById(category_id);
        List<ViewCategoryDto> list = new ArrayList<>();
        if (category.isPresent()) {
            if (categoryRepository.checkIfLeaf(category_id) == 0) {
                List<String> fields = new ArrayList<>();
                List<String> values = new ArrayList<>();

                ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                viewCategoriesDTO.setName(category.get().getName());

                List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category_id);
                for (Long l : longList) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, l));
                }
                viewCategoriesDTO.setFieldName(fields);
                viewCategoriesDTO.setValues(values);
                list.add(viewCategoriesDTO);
            } else {
                ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                viewCategoriesDTO.setName(category.get().getName());
                list.add(viewCategoriesDTO);
                List<Long> longList = categoryRepository.getIdsOfSubcategories(category_id);

                if (!longList.isEmpty()) {
                    for (Long l : longList) {
                        Optional<Category> category1 = categoryRepository.findById(l);
                        if (categoryRepository.checkIfLeaf(category1.get().getId()) == 0) {
                            List<String> fields = new ArrayList<>();
                            List<String> values = new ArrayList<>();

                            ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                            viewCategoriesDTO1.setName(category1.get().getName());

                            List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category1.get().getId());
                            for (Long l1 : longList1) {
                                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                                fields.add(categoryMetadataField.get().getName());
                                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category1.get().getId(), l1));
                            }
                            viewCategoriesDTO1.setFieldName(fields);
                            viewCategoriesDTO1.setValues(values);
                            list.add(viewCategoriesDTO1);
                        } else {
                            ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                            viewCategoriesDTO1.setName(category1.get().getName());
                            list.add(viewCategoriesDTO1);
                        }
                    }
                }
            }
            return list;
        } else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewCategoryDto> viewAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Long> longList = categoryRepository.getIdsOfCategory(paging);
        List<ViewCategoryDto> list = new ArrayList<>();
        for (Long l : longList) {
            Optional<Category> category = categoryRepository.findById(l);
            if (category.isPresent()) {
                if (categoryRepository.checkIfLeaf(category.get().getId()) == 0) {
                    List<String> fields = new ArrayList<>();
                    List<String> values = new ArrayList<>();

                    ViewCategoryDto viewCategoriesDTO1 = new ViewCategoryDto();
                    viewCategoriesDTO1.setName(category.get().getName());

                    List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
                    for (Long l1 : longList1) {
                        Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                        fields.add(categoryMetadataField.get().getName());
                        values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
                    }
                    viewCategoriesDTO1.setFieldName(fields);
                    viewCategoriesDTO1.setValues(values);
                    list.add(viewCategoriesDTO1);

                } else {
                    ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
                    viewCategoriesDTO.setName(category.get().getName());
                    list.add(viewCategoriesDTO);
                }

            } else {
                Long[] l1 = {};
                throw new NotFoundException(messageSource.getMessage("notfound.txt",l1,LocaleContextHolder.getLocale()));
            }
        }
        return list;
    }

    public void updateCategory(Category category, Long categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category1 = categoryRepository.findById(categoryId).get();
            category1.setName(category.getName());
            categoryRepository.save(category1);

        } else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));

        }
    }

    public void addMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {
        Long[] lo = {};
        if (categoryRepository.findById(categoryId).isPresent() && categoryRepository.checkIfLeaf(categoryId) == 0) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent())
            {
                String checkIfAlreadyPresent = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId,metadataId);
                if (checkIfAlreadyPresent==null) {
                    CategoryMetadataFieldValuesId categoryMetadataFieldValuesId = new CategoryMetadataFieldValuesId();
                    categoryMetadataFieldValuesId.setCategoryId(categoryRepository.findById(categoryId).get().getId());
                    categoryMetadataFieldValuesId.setCategoryMetadataFieldId(categoryMetadataFieldRepository.findById(metadataId).get().getId());

                    categoryMetadataFieldValues.setId(categoryMetadataFieldValuesId);
                    categoryMetadataFieldValues.setCategory(categoryRepository.findById(categoryId).get());
                    String[] valuesArray = categoryMetadataFieldValues.getFieldValues().split(",");
                    Set<String> s = new HashSet<>(Arrays.asList(valuesArray));
                    if (s.size() == valuesArray.length && s.size() >= 1 && valuesArray[0] != "")
                        categoryMetadataFieldValues.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                    else
                        throw new NullException(messageSource.getMessage("unique.txt", lo, LocaleContextHolder.getLocale()));
                    categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataFieldRepository.findById(metadataId).get());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
                }
                else
                {
                    throw new NullException("values are already present for this category id and metadata id");
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("metadata.txt",lo,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("message14.txt",lo,LocaleContextHolder.getLocale()));
        }

    }

    public void updateMetadataValues(CategoryMetadataFieldValues categoryMetadataFieldValues, Long categoryId, Long metadataId) {
        Long[] l = {};

        if (categoryRepository.findById(categoryId).isPresent()) {
            if (categoryMetadataFieldRepository.findById(metadataId).isPresent()) {
                if (categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId) != null) {
                    CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValuesRepository.getFieldValues(categoryId, metadataId);
                    categoryMetadataFieldValues1.setFieldValues(categoryMetadataFieldValues.getFieldValues());
                    categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
                } else {
                    throw new NotFoundException(messageSource.getMessage("combinationError.txt",l,LocaleContextHolder.getLocale()));
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("wrongMetadata.txt",l,LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewCategoryDto> viewAllCategoriesForSeller() {
        List<Object[]> list = sellerRepository.getSubcategory();
        List<ViewCategoryDto> list1 = new ArrayList<>();
        for (Object[] objects : list) {
            ViewCategoryDto viewCategoriesDTO = new ViewCategoryDto();
            viewCategoriesDTO.setName(objects[0].toString());
            Long categoryId = categoryRepository.getIdOfParentCategory(objects[0].toString());
            Optional<Category> category = categoryRepository.findById(categoryId);
            Set<CategoryMetadataFieldValues> set = category.get().getFieldValues();
            List<String> fields = new ArrayList<>();
            for (CategoryMetadataFieldValues categoryMetadataFieldValues : set) {
                fields.add(categoryMetadataFieldValues.getFieldValues());
                viewCategoriesDTO.setValues(fields);
            }
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            List<String> fields1 = new ArrayList<>();
            for (Long l : longList) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields1.add(categoryMetadataField.get().getName());
                viewCategoriesDTO.setFieldName(fields1);
            }
            list1.add(viewCategoriesDTO);
        }
        return list1;
    }

    public List<Object[]> getAllCategory() {
        List<Object[]> list = categoryRepository.getMainCategory();
        if (list.isEmpty()) {
            throw new NullException("no categories available");
        }
        return list;
    }

    public List<Object[]> getAllSubCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (categoryRepository.checkIfLeaf(id) == 1)
                return categoryRepository.getSubCategoryOfCategory(id);
            else {
                throw new NullException("category is a leaf node");
            }
        } else {
            throw new NotFoundException("category with this id is not present");
        }
    }

    public CategoryFilterDto getFilteringDetails(Long category_id) {
        Optional<Category> category = categoryRepository.findById(category_id);
        CategoryFilterDto filteringDTO = new CategoryFilterDto();

        if (category.isPresent() && categoryRepository.checkIfLeaf(category_id) == 0) {
            List<Long> longList = categoryMetadataFieldValuesRepository.getMetadataId(category_id);
            filteringDTO.setCategoryName(category.get().getName());
            List<String> fields = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for (Long l : longList) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l);
                fields.add(categoryMetadataField.get().getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category_id, l));
            }
            filteringDTO.setFields(fields);
            filteringDTO.setValues(values);
            Set<Product> set = category.get().getProducts();
            Double minPrice = 0.0;
            Double maxPrice = 0.0;
            TreeSet<Double> doubles = new TreeSet<>();
            List<String> brands = new ArrayList<>();
            for (Product product : set) {
                brands.add(product.getBrand());
                Set<ProductVariation> set1 = product.getVariations();
                for (ProductVariation productVariation : set1) {
                    doubles.add(productVariation.getPrice());
                }
            }
            filteringDTO.setBrands(brands);
            Double[] d = doubles.toArray(new Double[doubles.size()]);
            filteringDTO.setMaximumPrice(d[d.length - 1]);
            filteringDTO.setMinimumPrice(d[0]);
        } else {
            Long[] l =  {};
            throw new NotFoundException(messageSource.getMessage("message15.txt",l,LocaleContextHolder.getLocale()));
        }
        return filteringDTO;
    }

    public void deleteCategoryMetadataField(Long id) {
        Long[] l = {};

        if (categoryMetadataFieldRepository.findById(id).isPresent())
        {
            categoryMetadataFieldRepository.remove(id);
            categoryMetadataFieldRepository.deleteMetadatField(id);

        } else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l, LocaleContextHolder.getLocale()));
        }
    }
}