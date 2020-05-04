package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.dto.CategoryFilterDto;
import com.tothenew.ecommerce.dto.ViewCategoryDto;
import com.tothenew.ecommerce.entity.Category;
import com.tothenew.ecommerce.entity.CategoryMetadataField;
import com.tothenew.ecommerce.entity.CategoryMetadataFieldValues;
import com.tothenew.ecommerce.repository.CategoryMetadataFieldRepository;
import com.tothenew.ecommerce.services.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryService categoryService;

    //Admin APIs
    @ApiOperation("to add metadata fields")
    @PostMapping("/admin/addCategoryMetadataField")
    public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField) {
        categoryService.addCategoryMetadataField(categoryMetadataField);
        return "CategoryMetadataField is successfully created";
    }

    @ApiOperation("to list all metadata field values")
    @PutMapping("/admin/viewCategoryMetadataField")
    public List<CategoryMetadataField> viewCategoryMetadataField(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                                 @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){
        return categoryService.viewCategoryMetadataField(pageNo, pageSize, sortBy);
    }

    @ApiOperation("to add a new subcategory")
    @PostMapping("/admin/addNewCategory/{parent_category_id}")
    public String addNewSubCategory(@Valid @PathVariable(name = "parent_category_id") Long parent_category_id, @RequestBody Category category) {
        categoryService.addNewSubCategory(parent_category_id, category);
        return "subcategory added successfully";
    }

    @ApiOperation("to add a main category")
    @PostMapping("/admin/addNewCategory")
    public ResponseEntity addMainCategory(@Valid @RequestBody Category category)
    {
        return categoryService.addMainCategory(category);
    }

    @ApiOperation("to view a single category")
    @GetMapping("/admin/viewACategory/{id}")
    public List<ViewCategoryDto> viewCategory(@PathVariable Long id) {
        return categoryService.viewACategory(id);
    }

    @ApiOperation("to view all categories")
    @GetMapping("/admin/viewAllCategories")
    public List<ViewCategoryDto> getAllCategories(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        return categoryService.viewAllCategories(pageNo, pageSize, sortBy);
    }

    @ApiOperation(("to delete a metadata category"))
    @GetMapping("/admin/deleteCategoryMetadata/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryMetadataField(id);
        return "successfully deleted";
    }

    @ApiOperation("to update category details")
    @PutMapping("/admin/updateCategory/{categoryId}")
    public String updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "categoryId") Long categoryId) {
        categoryService.updateCategory(category, categoryId);
        return "Category successfully updated";
    }

    @ApiOperation("to add metadata field values for a particular category and metadata field ")
    @PostMapping("/admin/addMetadataValues/{categoryId}/{metadataId}")
    public String addMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                    @PathVariable(value = "categoryId") Long categoryId,
                                    @PathVariable(value = "metadataId") Long metadataId) {
        categoryService.addMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);
        return "values added";
    }

    @ApiOperation("to update metadata field values")
    @PutMapping("/admin/updateMetadataValues/{categoryId}/{metadataId}")
    public void updateMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                     @PathVariable(value = "categoryId") Long categoryId,
                                     @PathVariable(value = "metadataId") Long metadataId) {
        categoryService.updateMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);
    }

    //Seller APIs

    @ApiOperation("to view all categories")
    @GetMapping("/seller/viewCategoriesForSeller")
    public List<ViewCategoryDto> viewCategoriesDTOS() {
        return categoryService.viewAllCategoriesForSeller();
    }

    //Customer APIs

    @ApiOperation("to view all categories")
    @GetMapping("/customer/viewCategoriesForCustomer")
    public List<Object[]> getMainCategoriesForCustomer() {
        return categoryService.getAllCategory();
    }

    @ApiOperation("to view a single category")
    @GetMapping("/customer/viewCategoriesForCustomer/{id}")
    public List<Object[]> getSubCategory(@PathVariable(name = "id") Long id) {
        List<Object[]> list = categoryService.getAllSubCategory(id);
        return list;
    }

    @ApiOperation("to get filtering details of a category")
    @GetMapping("/customer/filtering/{id}")
    public CategoryFilterDto getFilteringDetails(@PathVariable Long id) {
        return categoryService.getFilteringDetails(id);
    }
}
