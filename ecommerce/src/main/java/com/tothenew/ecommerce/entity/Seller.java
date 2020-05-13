package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "id")
@Audited
public class Seller extends User implements Serializable {

    @NotNull
    @Column(unique = true)
    //@Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
    private String gst;
    @NotNull
    @Column(unique = true)
    private Long companyContact;
    @NotNull
    @Column(unique = true)
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

    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new HashSet<Product>();

            products.add(product);

            product.setSeller(this);
        }
    }
}

