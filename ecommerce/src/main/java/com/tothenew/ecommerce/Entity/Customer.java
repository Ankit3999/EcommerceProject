package com.tothenew.ecommerce.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{

    Long contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Product_Review> product_reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToOne(mappedBy = "customer")
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

    public List<Product_Review> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(List<Product_Review> product_reviews) {
        this.product_reviews = product_reviews;
    }

    public Customer(){
        Role rolesModel2= new Role();
        rolesModel2.setRole("CUSTOMER");
        Set<Role> rolesModels = new HashSet<>();
        rolesModels.add(rolesModel2);
        this.setRoles(rolesModels);
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}
