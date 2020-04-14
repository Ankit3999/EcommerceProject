package com.tothenew.ecommerce.Entity;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class CategoryMetadataField {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValues;

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValues() {
        return categoryMetadataFieldValues;
    }

    public void setCategoryMetadataFieldValues(Set<CategoryMetadataFieldValues> categoryMetadataFieldValues) {
        this.categoryMetadataFieldValues = categoryMetadataFieldValues;
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
