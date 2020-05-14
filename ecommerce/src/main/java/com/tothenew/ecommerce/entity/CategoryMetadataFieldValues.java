package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
public class CategoryMetadataFieldValues  implements Serializable {

    @EmbeddedId
    private CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @Column(name = "field_values", insertable = false, updatable = false)
    private String fieldValues;

    @Column(name = "created_date", insertable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_date", insertable = false, updatable = false)
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_by", insertable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by", insertable = false, updatable = false)
    @LastModifiedBy
    private String modifiedBy;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

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
