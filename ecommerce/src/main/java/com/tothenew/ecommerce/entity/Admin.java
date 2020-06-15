package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Audited
public class Admin extends User implements Serializable {
    public Admin(String username, String email, String firstName, String middleName, String lastName) {
        super(username, email, firstName, middleName, lastName);
    }
    public Admin(){}
}
