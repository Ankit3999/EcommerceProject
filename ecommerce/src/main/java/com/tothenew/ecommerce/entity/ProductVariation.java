package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tothenew.ecommerce.utilities.HashMapConverter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table
@Audited
@EntityListeners(AuditingEntityListener.class)
public class ProductVariation implements Serializable {
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

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    @Convert(converter = HashMapConverter.class)
    private Map<String,Object> infoAttributes;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<Cart> carts;
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

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void addCarts(Cart cart)
    {
        if (carts==null)
        {
            carts = new HashSet<>();
        }
        carts.add(cart);
    }
}
