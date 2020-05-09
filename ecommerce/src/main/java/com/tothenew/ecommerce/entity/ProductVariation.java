package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tothenew.ecommerce.utilities.HashMapConverter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table
public class ProductVariation extends AuditInformation implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    //private Long productId;
    @Positive
    @Column(nullable = false)
    private Integer quantityAvailable;
    @Positive
    @Column(nullable = false)
    private Double price;
    private String metadata;
    private String primaryImageName;
    private String infoJson;
    private Boolean isActive;

    @Convert(converter = HashMapConverter.class)
    private Map<String,Object> infoAttributes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
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

    public ProductVariation(Integer quantityAvailable, Double price) {
        this.quantityAvailable = quantityAvailable;
        this.price = price;
    }

    public ProductVariation() {
    }

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }

    public Map<String, Object> getInfoAttributes() {
        return infoAttributes;
    }

    public void setInfoAttributes(Map<String, Object> infoAttributes) {
        this.infoAttributes = infoAttributes;
    }

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


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
