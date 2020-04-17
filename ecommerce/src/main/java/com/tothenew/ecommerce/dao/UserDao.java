package com.tothenew.ecommerce.dao;

import com.tothenew.ecommerce.entity.Role;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
@Service
public class UserDao {
    @Autowired
    UserRepository userRepository;
    public AppUser loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
    /*    Set<Role> roles = user.addRole();
        Iterator<Role> roleIterator = roles.iterator();
        String ro = null;
        List<GrantAuthorityImpl> list = new ArrayList<>();
        while (roleIterator.hasNext())
        {
            Role role = roleIterator.next();
            list.add(new GrantAuthorityImpl(role.getRole()));
        }
        System.out.println(user);*/
        if (user != null) {
            return new AppUser(user);
        } else {
            throw new UsernameNotFoundException("user  " + user.getEmail() + " was not found");
        }
    }
}