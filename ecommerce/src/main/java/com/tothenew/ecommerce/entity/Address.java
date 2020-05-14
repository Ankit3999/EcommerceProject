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
@Table
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private Long zipCode;
    private String label; /*(Ex. office/home)*/

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address() {
    }

    public Address(String city, String state, String country, String addressLine, Long zipCode, String label) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressLine = addressLine;
        this.zipCode = zipCode;
        this.label = label;
    }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

