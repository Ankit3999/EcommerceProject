package com.tothenew.ecommerce.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;

@Component
public class ProductVariationDto extends ProductDto{
    private Long id;

    @NotNull
    private Long productId;
    private Integer quantityAvailable;
    private Double price;
    private Set<String> images;
    List<String> field = new ArrayList<>();
    List<String> values = new ArrayList<>();

    @NotNull
    private Map<String, String> attributes = new LinkedHashMap<>();

    private ProductDto productDto;

    public ProductVariationDto() {
    }

    public ProductVariationDto(Long id, @NotNull Long productId, Integer quantityAvailable, Double price, Set<String> images, @NotNull Map<String, String> attributes, ProductDto productDto) {
        this.id = id;
        this.productId = productId;
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.images = images;
        this.attributes = attributes;
        this.productDto = productDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
