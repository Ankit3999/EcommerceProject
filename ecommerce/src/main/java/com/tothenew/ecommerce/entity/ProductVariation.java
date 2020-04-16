package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class ProductVariation {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long quantityAvailable;
    private Long price;
    private String metadata;
    private String primaryImageName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Product_ProductVariation_id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Product_Variation_id")
    private OrderProduct order_product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Cart_Product_Variation_id")
    private Cart cart;
    /*
"METADATA (Type: JSON - available in mysql to store a JSON as it is.)
(Note: will contain all the information regarding variations in JSON format)
(All variations of same category will have a fixed similar JSON structure)"
PRIMARY_IMAGE_NAME*/

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public OrderProduct getOrder_product() {
        return order_product;
    }

    public void setOrder_product(OrderProduct order_product) {
        this.order_product = order_product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }
}
