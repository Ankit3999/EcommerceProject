package com.tothenew.ecommerce.Entity;

import javax.persistence.*;

@Entity
@Table
public class CategoryMetadataFieldValues {
    private String values;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryMetadataFieldId")
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
