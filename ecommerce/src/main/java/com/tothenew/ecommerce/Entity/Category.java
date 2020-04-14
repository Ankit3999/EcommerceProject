package com.tothenew.ecommerce.Entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @UniqueElements
    private String name;
    //private Long parentId;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Category> subCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> fieldValues;

    Category(){
        parentCategory=null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

