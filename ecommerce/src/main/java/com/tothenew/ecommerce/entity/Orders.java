package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ids;
    private Long amountPaid;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    private String paymentMethod;
/*    private String CUSTOMER_ADDRESS_CITY;
    private String CUSTOMER_ADDRESS_STATE;
    private String CUSTOMER_ADDRESS_COUNTRY;
    private String CUSTOMER_ADDRESS_ADDRESS_LINE;
    private String CUSTOMER_ADDRESS_ZIP_CODE;
    private String CUSTOMER_ADDRESS_LABEL;*/

    @Column(name = "created_date", insertable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_date", insertable = false, updatable = false)
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_by", insertable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by", insertable = false, updatable = false)
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

    @Embedded
    OrderAddress orderAddress;

    public Orders() {
    }

    public Orders(Long amountPaid, Date dateCreated, String paymentMethod, Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, Customer customer) {
        this.amountPaid = amountPaid;
        this.dateCreated = dateCreated;
        this.paymentMethod = paymentMethod;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.customer = customer;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<OrderProduct> order_product;

    public Set<OrderProduct> getOrder_product() {
        return order_product;
    }

    public void setOrder_product(Set<OrderProduct> order_product) {
        this.order_product = order_product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }



    /*ID
CUSTOMER_USER_ID
AMOUNT_PAID
DATE_CREATED
PAYMENT_METHOD
CUSTOMER_ADDRESS_CITY
CUSTOMER_ADDRESS_STATE
CUSTOMER_ADDRESS_COUNTRY
CUSTOMER_ADDRESS_ADDRESS_LINE
CUSTOMER_ADDRESS_ZIP_CODE
CUSTOMER_ADDRESS_LABEL (Ex. office/home)*/
}
