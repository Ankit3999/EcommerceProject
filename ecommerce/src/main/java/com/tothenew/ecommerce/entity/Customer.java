package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
@Audited
public class Customer extends User implements Serializable {

    Long contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> product_reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<ProductReview> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(List<ProductReview> product_reviews) {
        this.product_reviews = product_reviews;
    }

    public void addReview(ProductReview review){
        if(review != null){
            if(product_reviews == null)
                product_reviews = new ArrayList<>();

            product_reviews.add(review);
            review.setCustomer(this);
        }
    }

    public Customer(){
        this.addRole(new Role(1003l, "ROLE_CUSTOMER"));
    }

    public Customer(String username, String email, String firstName, String middleName, String lastName, Long contact) {
        super(username, email, firstName, middleName, lastName);
        //this.addRole(new Role(1003l, "ROLE_CUSTOMER"));
        this.contact = contact;
    }

    public Customer(String username, String email, String firstName, String middleName, String lastName, Long contact, List<ProductReview> product_reviews, List<Orders> orders, Cart cart) {
        super(username, email, firstName, middleName, lastName);
        this.contact = contact;
        this.product_reviews = product_reviews;
        this.orders = orders;
        this.cart = cart;
    }

    public Customer(Long ID, String USERNAME, String EMAIL, String FIRST_NAME, String MIDDLE_NAME, String LAST_NAME, String PASSWORD, Boolean IS_DELETED, Boolean IS_ACTIVE, Set<Role> roles, Long contact, List<ProductReview> product_reviews, List<Orders> orders, Cart cart) {
        super(ID, USERNAME, EMAIL, FIRST_NAME, MIDDLE_NAME, LAST_NAME, PASSWORD, IS_DELETED, IS_ACTIVE, roles);
        this.contact = contact;
        this.product_reviews = product_reviews;
        this.orders = orders;
        this.cart = cart;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}
