package com.tothenew.ecommerce.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ProductVariationDto {
    private Long id;

    @NotNull
    private Long productId;
    private Integer quantityAvailable;
    private Double price;
    private Set<String> images;

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
}
