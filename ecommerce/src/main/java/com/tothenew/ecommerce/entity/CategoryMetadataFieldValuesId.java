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

@Embeddable
@Audited
@EntityListeners(AuditingEntityListener.class)
public class CategoryMetadataFieldValuesId implements Serializable {
    private Long categoryId;
    private Long categoryMetadataFieldId;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
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
