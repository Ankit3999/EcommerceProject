package com.tothenew.ecommerce.entity;

import com.tothenew.ecommerce.validator.ValidGST;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User{
    private Long userId;
    //@ValidGST
    private String gst;
    private Long companyContact;
    private String companyName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products;

    public Seller(){
        this.addRole(new Role(1002l, "ROLE_SELLER"));
    }

    public Seller(String username, String email, String firstName, String middleName, String lastName, String GST, String companyName, Long companyContact) {
        super(username, email, firstName, middleName, lastName);
        this.gst = GST;
        this.companyName = companyName;
        this.companyContact = companyContact;
        this.addRole(new Role(1002l, "ROLE_SELLER"));
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}

