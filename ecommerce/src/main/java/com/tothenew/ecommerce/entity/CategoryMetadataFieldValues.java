package com.tothenew.ecommerce.entity;

import javax.persistence.*;

@Entity
public class CategoryMetadataFieldValues {

    @EmbeddedId
    private CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @Column(name = "field_values")
    private String fieldValues;

    public CategoryMetadataFieldValues() {
    }

    public CategoryMetadataFieldValues(String values) {
        this.fieldValues = values;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoriesId")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryMetaFieldId")
    private CategoryMetadataField categoryMetadataField;

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public CategoryMetadataFieldValuesId getId() {
        return id;
    }

    public void setId(CategoryMetadataFieldValuesId id) {
        this.id = id;
    }


}
