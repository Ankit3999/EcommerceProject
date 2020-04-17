package com.tothenew.ecommerce.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class ProductDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private Long categoryId;
    private CategoryDto categoryDto;
    private String description;
    private Boolean isReturnable = false;
    private Boolean isCancellable = false;

    public ProductDto() {
    }

    public ProductDto(Long id, @NotNull String name, @NotNull String brand, @NotNull Long categoryId, CategoryDto categoryDto, String description, Boolean isReturnable, Boolean isCancellable) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.categoryDto = categoryDto;
        this.description = description;
        this.isReturnable = isReturnable;
        this.isCancellable = isCancellable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }
}
