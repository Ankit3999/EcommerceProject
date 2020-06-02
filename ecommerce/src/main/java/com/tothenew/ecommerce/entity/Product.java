package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table

@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {
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

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setVariations(Set<ProductVariation> variations) {
        this.variations = variations;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductVariation> variations;

 /*   @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductReview> product_reviews;*/

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

    /*public void addReview(ProductReview review){
        if(review != null){
            if(product_reviews== null)
                product_reviews = new ArrayList<>();

            product_reviews.add(review);
            review.setProduct(this);
        }
    }*/

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
