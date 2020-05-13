package com.tothenew.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tothenew.ecommerce.enums.Status;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Audited
public class OrderStatus extends AuditInformation implements Serializable {
    @Id
    @GeneratedValue
    private Long orderProductId;
    private Status fromStatus;
    private Status toStatus;
    private String transitionNotesComments;

    @JsonIgnore
    @OneToMany(mappedBy = "order_status", cascade = CascadeType.ALL)
    private Set<OrderProduct> order_product;

    public Set<OrderProduct> getOrder_product() {
        return order_product;
    }

    public void setOrder_product(Set<OrderProduct> order_product) {
        this.order_product = order_product;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Status getFromStatus() { return fromStatus; }

    public void setFromStatus(Status fromStatus) { this.fromStatus = fromStatus; }

    public Status getToStatus() {
        return toStatus;
    }

    public void setToStatus(Status toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }

    /*ORDER_PRODUCT_ID
FROM_STATUS
TO_STATUS
TRANSITION_NOTES_COMMENTS*/
}
