package com.tothenew.ecommerce.dao;

import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Role;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AppUser implements UserDetails {

    @Autowired
    AppUserDetailsService appUserDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDao userDao;

    private Long id;

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;

    private boolean isDeleted;
    private boolean isActive;
    private boolean isExpired;
    private boolean isLocked;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;

    private Set<Role> roles;

    private Set<Address> addresses;

    //List<GrantAuthorityImpl> grantAuthorities;
    public AppUser(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.isActive = user.getActive();
        this.isDeleted = user.getDeleted();
        this.isExpired = user.isExpired();
        this.isLocked = user.isLocked();

        this.roles = new HashSet<>(user.getRoles());

        this.addresses = new HashSet<Address>(user.getAddresses());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*System.out.println("get authorities");
        return grantAuthorities;*/
        return roles;
    }
    @Override
    public String getPassword()
    {
        System.out.println(password);
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }
    @Override
    public boolean isAccountNonLocked() { return !isLocked; }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
