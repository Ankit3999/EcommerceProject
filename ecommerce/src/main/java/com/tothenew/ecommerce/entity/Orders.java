package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

@Entity
@Table(name = "orders")
@Audited
public class Orders extends AuditInformation implements Serializable {
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

    @Embedded
    OrderAddress orderAddress;

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private OrderProduct order_product;

    public OrderProduct getOrder_product() {
        return order_product;
    }

    public void setOrder_product(OrderProduct order_product) {
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
