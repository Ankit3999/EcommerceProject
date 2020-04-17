package com.tothenew.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Admin extends User{
    public Admin(String username, String email, String firstName, String middleName, String lastName) {
        super(username, email, firstName, middleName, lastName);
    }
    public Admin(){}
}
