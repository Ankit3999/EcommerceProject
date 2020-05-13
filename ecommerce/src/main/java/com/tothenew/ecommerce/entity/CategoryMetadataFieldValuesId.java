package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Audited
public class CategoryMetadataFieldValuesId implements Serializable {
    private Long categoryId;
    private Long categoryMetadataFieldId;

    public CategoryMetadataFieldValuesId() {
    }

    public CategoryMetadataFieldValuesId(Long categoryId, Long categoryMetadataFieldId) {
        this.categoryId = categoryId;
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryMetadataFieldId() {
        return categoryMetadataFieldId;
    }

    public void setCategoryMetadataFieldId(Long categoryMetadataFieldId) {
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }
}
