package com.tothenew.ecommerce.Entity;

import com.tothenew.ecommerce.Validator.ValidGST;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User{
    private Long userId;
    @ValidGST
    private Long gst;
    private Long companyContact;
    private String companyName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products;

    public Seller(){
        Role rolesModel2= new Role();
        rolesModel2.setRole("SELLER");
        Set<Role> rolesModels = new HashSet<>();
        rolesModels.add(rolesModel2);
        this.setRoles(rolesModels);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGst() {
        return gst;
    }

    public void setGst(Long gst) {
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

