package com.tothenew.ecommerce.entity;

import javax.persistence.*;

@Entity
public class CategoryMetadataFieldValues {

    @EmbeddedId
    private CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @Column(name = "field_values")
    private String values;

    public CategoryMetadataFieldValues(String values) {
        this.values = values;
    }

    @ManyToOne
    @JoinColumn(name = "categoriesId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "categoryMetaFieldId")
    private CategoryMetadataField categoryMetadataField;

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
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
}
