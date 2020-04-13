package com.tothenew.ecommerce.Bootloader;


import com.tothenew.ecommerce.Entity.User;
import com.tothenew.ecommerce.Entity.Role;
import com.tothenew.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner
{
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        if(userRepository.count()<1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User user = new User();
            user.setUsername("ankit");
            user.setLastName("kumar");
            user.setMiddleName("sagar");
            user.setPassword(passwordEncoder.encode("pass"));
            Role rolesModel = new Role();
            Role rolesModel1 = new Role();
            Role rolesModel2 = new Role();
            rolesModel.setRole("ROLE_ADMIN");
            rolesModel1.setRole("ROLE_SELLER");
            rolesModel2.setRole("ROLE_CUSTOMER");
            Set<Role> rolesModels = new HashSet<>();
            rolesModels.add(rolesModel);
            rolesModels.add(rolesModel1);
            user.setRoles(rolesModels);

            User user1 = new User();
            user1.setUsername("ewer");
            user1.setPassword(passwordEncoder.encode("pass"));
            Set<Role> roles1 = new HashSet<>();
            roles1.add(rolesModel2);
            user1.setRoles(roles1);

            userRepository.save(user);
            userRepository.save(user1);

            System.out.println("Total users saved::" + userRepository.count());

        }

    }
}