package com.tothenew.ecommerce.Entity;

import javax.persistence.Embeddable;

@Embeddable
public class OrderAddress {
    private Long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private Long zipCode;
    private String label; /*(Ex. office/home)*/


    public OrderAddress(Address address){
        this.id=address.getId();
        this.city=address.getCity();
        this.state= address.getState();
        this.country=address.getCountry();
        this.addressLine=address.getAddressLine();
        this.zipCode=address.getZipCode();
        this.label=address.getLabel();

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
