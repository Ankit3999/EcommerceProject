package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Product {
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

    public List<ProductReview> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(List<ProductReview> product_reviews) {
        this.product_reviews = product_reviews;
    }

    public Set<ProductVariation> getVariations() {
        return variations;
    }

    public void setVariations(Set<ProductVariation> variations) {
        this.variations = variations;
    }

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
