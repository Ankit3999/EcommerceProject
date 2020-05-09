package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;

@Entity
@Table
public class OrderProduct extends AuditInformation implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long price;
    private Long productVariationMetadata;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    Orders orders;

    @OneToOne(mappedBy = "order_product")
    ProductVariation product_variation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "OrderProductStatusId")
    private OrderStatus order_status;

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }

    public ProductVariation getProduct_variation() {
        return product_variation;
    }

    public void setProduct_variation(ProductVariation product_variation) {
        this.product_variation = product_variation;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
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

    public Long getProductVariationMetadata() {
        return productVariationMetadata;
    }

    public void setProductVariationMetadata(Long productVariationMetadata) {
        this.productVariationMetadata = productVariationMetadata;
    }



   /*ID
ORDER_ID
QUANTITY
PRICE
PRODUCT_VARIATION_ID
PRODUCT_VARIATION_METADATA*/
}
