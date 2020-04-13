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

    @OneToMany(mappedBy = "CategoryMetadataField", cascade = CascadeType.ALL)
    private Set<CategoryMetadataFieldValues> fieldValuesSet;

    public Set<CategoryMetadataFieldValues> getFieldValuesSet() {
        return fieldValuesSet;
    }

    public void setFieldValuesSet(Set<CategoryMetadataFieldValues> fieldValuesSet) {
        this.fieldValuesSet = fieldValuesSet;
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
