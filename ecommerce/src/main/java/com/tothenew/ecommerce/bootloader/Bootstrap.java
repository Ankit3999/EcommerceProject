package com.tothenew.ecommerce.bootloader;


import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.repository.RoleRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        if(userRepository.count()<1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Role admin = new Role(1001l, "ROLE_ADMIN");
            Role seller = new Role(1002l, "ROLE_SELLER");
            Role customer = new Role(1003l, "ROLE_CUSTOMER");
            roleRepository.save(admin);
            roleRepository.save(customer);
            roleRepository.save(seller);

            Admin admin1 = new Admin("ankit12","myemail@ttn.com", "admin", "", "admin");
            admin1.setPassword(passwordEncoder.encode("pass"));
            //admin1.addRole(admin);
            //admin1.addRole(seller);
            admin1.addRole(admin);
            admin1.addAddress(new Address("noida","haryana", "india", "04/70", 778884l, "home"));
            admin1.addAddress(new Address("ndls", "delhi", "india", "B/90", 23131l, "work"));
            admin1.setActive(true);

            userRepository.save(admin1);

            Customer customer1 = new Customer("sagar12","customer@ttn.com", "customer", "", "customer", 9873556644l);
            customer1.setPassword(passwordEncoder.encode("pass"));
            customer1.addRole(customer);
            customer1.addAddress(new Address("gurgaon","haryana", "india", "C64", 778344l, "home"));
            customer1.setActive(true);
            userRepository.save(customer1);

            Seller seller1 = new Seller("anku12","seller.seller@ttn.com", "seller", "", "seller","bh7ht754r5", "amalgam pvt. lmt.", 9999988817l);
            seller1.setPassword(passwordEncoder.encode("pass"));
            seller1.addRole(seller);
            seller1.addAddress(new Address("kanpur", "UP", "india", "fg95", 2342342l, "home"));
            seller1.setActive(true);

           // userRepository.save(seller1);

            System.out.println("Total users saved::"+userRepository.count());

   /*         User user = new User();
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
            user1.setActive(true);
            user1.setEmail("786ankit555@gmail.com");
            user1.setFirstName("sagar");
            user1.setId(001l);

            Set<Role> roles1 = new HashSet<>();
            roles1.add(rolesModel2);
            user1.setRoles(roles1);

            userRepository.save(user);
            userRepository.save(user1);

            System.out.println("Total users saved::" + userRepository.count());*/

        }

    }
}