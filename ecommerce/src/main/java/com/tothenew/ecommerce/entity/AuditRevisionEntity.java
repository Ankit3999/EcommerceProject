package com.tothenew.ecommerce.entity;

import com.tothenew.ecommerce.event.AuditRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;
@Entity
@RevisionEntity(AuditRevisionListener.class)
@Table(name = "REVINFO", schema = "audit")
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "REVTSTMP")),
        @AttributeOverride(name = "id", column = @Column(name = "REV"))})
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "USERNAME", nullable = false)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
