package com.tothenew.ecommerce.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue
    private Long customerUserId;
    private Long quantity;
    private Boolean isWishlistItem;
    private Long productVariationId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "cart")
    private Set<Product_Variation> product_variationSet;

    public Set<Product_Variation> getProduct_variationSet() {
        return product_variationSet;
    }

    public void setProduct_variationSet(Set<Product_Variation> product_variationSet) {
        this.product_variationSet = product_variationSet;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Long customerUserId) {
        this.customerUserId = customerUserId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Boolean getWishlistItem() {
        return isWishlistItem;
    }

    public void setWishlistItem(Boolean wishlistItem) {
        isWishlistItem = wishlistItem;
    }

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }
}

