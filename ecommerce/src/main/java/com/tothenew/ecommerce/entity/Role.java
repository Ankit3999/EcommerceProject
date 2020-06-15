package com.tothenew.ecommerce.entity;


import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@Audited
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(Long id, String role, Set<User> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role() {
    }

    public Role(Long i, String role) {
        this.id=i;
        this.role=role;
    }
    public Role(String role) {
        this.role=role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        if(users == null)
            users = new HashSet<>();

        users.add(user);
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
