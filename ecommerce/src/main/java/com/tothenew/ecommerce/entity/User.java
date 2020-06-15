package com.tothenew.ecommerce.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.Timestamp;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    @Column(unique=true)
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private Boolean isDeleted=false;
    private Boolean isActive=false;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private boolean isAccountNonLocked;
    private boolean passwordExpire=false;

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

    public User(Long id) {
        this.id=id;
    }

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

    public boolean isPasswordExpire() { return passwordExpire; }

    public void setPasswordExpire(boolean passwordExpire) { this.passwordExpire = passwordExpire; }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses;

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public User(){ }

    public User(String username, String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, boolean isExpired, boolean isLocked, boolean isAccountNonLocked, boolean passwordExpire, Date createdDate, Date modifiedDate, String createdBy, String modifiedBy) {

        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.isAccountNonLocked = isAccountNonLocked;
        this.passwordExpire = passwordExpire;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public User(String username, String email, String firstName, String middleName, String lastName) {
        this.username=username;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public User(Long ID, String USERNAME, String EMAIL, String FIRST_NAME, String MIDDLE_NAME, String LAST_NAME, String PASSWORD, Boolean IS_DELETED, Boolean IS_ACTIVE, Set<Role> roles) {
        this.id = ID;
        this.username = USERNAME;
        this.email = EMAIL;
        this.firstName = FIRST_NAME;
        this.middleName = MIDDLE_NAME;
        this.lastName = LAST_NAME;
        this.password = PASSWORD;
        this.isDeleted = IS_DELETED;
        this.isActive = IS_ACTIVE;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void addAddress(Address address){
        if(address!=null){
            if(addresses == null)
                addresses = new HashSet<Address>();

            System.out.println("address added");
            address.setUser(this);
            addresses.add(address);
        }
    }

    public void addRole(Role role){
        if(role!=null){
            if(roles==null)
                roles = new HashSet<>();

            roles.add(role);
        }
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
