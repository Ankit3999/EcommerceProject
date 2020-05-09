package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Product extends AuditInformation implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Boolean isReturnable =false;
    private Boolean isCancellable = false;
    private Boolean isActive = false;
    private Boolean isDeleted = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ProductVariation> variations;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductReview> product_reviews;

    public Product() {
    }

    public Product(String name, String description, String brand) {
        this.name = name;
        this.description = description;
        this.brand = brand;
    }

    public Product(String productName, String brand, Boolean isCancellable, Boolean isReturnable, String description, boolean isActive) {
        this.name=productName;
        this.brand=brand;
        this.isCancellable=isCancellable;
        this.isReturnable=isReturnable;
        this.description=description;
        this.isActive=isActive;
    }

    public List<ProductReview> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(List<ProductReview> product_reviews) {
        this.product_reviews = product_reviews;
    }

    public Set<ProductVariation> getVariations() {
        return variations;
    }

/*
    public void setVariations(Set<ProductVariation> variations) {
        this.variations = variations;
    }
*/

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addVariation(ProductVariation variation){
        if(variation != null){
            if(variations == null)
                variations = new HashSet<>();

            variations.add(variation);
            variation.setProduct(this);
        }
    }

    public void addReview(ProductReview review){
        if(review != null){
            if(product_reviews== null)
                product_reviews = new ArrayList<>();

            product_reviews.add(review);
            review.setProduct(this);
        }
    }

    /*ID
SELLER_USER_ID
NAME
DESCRIPTION
"CATEGORY_ID
* Only the leaf category node would be associated with a product"
IS_CANCELLABLE
IS_RETURNABLE
BRAND
IS_ACTIVE*/

}
