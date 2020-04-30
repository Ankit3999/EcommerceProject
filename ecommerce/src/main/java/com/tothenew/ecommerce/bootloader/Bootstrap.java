package com.tothenew.ecommerce.bootloader;


import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Bootstrap implements ApplicationRunner
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;

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
            admin1.addRole(admin);
            admin1.addRole(seller);
            admin1.addRole(customer);
            admin1.addAddress(new Address("noida","haryana", "india", "04/70", 778884l, "home"));
            admin1.addAddress(new Address("ndls", "delhi", "india", "B/90", 23131l, "work"));
            admin1.setActive(true);
            admin1.setAccountNonLocked(true);

            userRepository.save(admin1);

            Customer customer1 = new Customer("sagar12","customer@ttn.com", "customer", "", "customer", 9873556644l);
            customer1.setPassword(passwordEncoder.encode("pass"));
            customer1.addRole(customer);
            customer1.addAddress(new Address("gurgaon","haryana", "india", "C64", 778344l, "home"));
            customer1.setActive(true);
            userRepository.save(customer1);

            /*Seller seller1 = new Seller("anku12","seller.seller@ttn.com", "seller", "", "seller","bh7ht754r5", "amalgam pvt. lmt.", 9999988817l);
            seller1.setPassword(passwordEncoder.encode("pass"));
            seller1.addRole(seller);
            seller1.addAddress(new Address("kanpur", "UP", "india", "fg95", 2342342l, "home"));
            seller1.setActive(true);

            userRepository.save(seller1);*/

            System.out.println("Total users saved::"+userRepository.count());



            //products and category

            Product product1 = new Product("UCB T-Shirt", "comfortable", "UCB");
            Product product2 = new Product("RedTape Jeans", "slim fit", "RedTape");
            Product product3 = new Product("Nike shoes", "light weight", "Nike");

            product1.setId(100L);

            Category fashion = new Category("fashion");
            Category clothing = new Category("clothing");
            fashion.addSubCategory(clothing);
            Category men = new Category("men");
            Category women = new Category("women");
            clothing.addSubCategory(men);
            clothing.addSubCategory(women);

            categoryRepository.save(fashion);

            System.out.println("total categories saved - "+ categoryRepository.count());


            ProductVariation mSize = new ProductVariation(5, 1500d);
            Map<String, Object> attributes1= new HashMap<>();
            attributes1.put("size", "M-Size");
            attributes1.put("gender", "female");
            mSize.setInfoAttributes(attributes1);

            ProductVariation lSize = new ProductVariation(3, 1600d);
            Map<String, Object> attributes2= new HashMap<>();
            attributes2.put("size", "L-Size");
            attributes2.put("gender", "male");
            lSize.setInfoAttributes(attributes2);


            product1.setCategory(men);
            product1.addVariation(mSize);
            product1.addVariation(lSize);

            //seller1.addProduct(product1);

            productRepository.save(product1);


//  =============================================================================
            ProductReview review1 = new ProductReview("awesome", "4.3");
            ProductReview review2 = new ProductReview("comfortable", "4.8");


            //order


            Orders order1 = new Orders();
            order1.setDateCreated(new Date());
            order1.setPaymentMethod("Cash on Delivery");
            order1.setIds(2009992L);
            Customer custom = customerRepository.findByEmail("customer@ttn.com");
            order1.setCustomer(custom);
            order1.setOrderAddress(new OrderAddress(new Address("patna", "bihar", "india", "56/09", 800024l, "home")));

            //ProductVariation buy1 = productVariationRepository.findById(14L).get();
            //ProductVariation buy2 = productVariationRepository.findById(15L).get();

            /*OrderProduct orderProduct1 = new OrderProduct();
            orderProduct1.setProduct_variation(buy1);

            OrderProduct orderProduct2 = new OrderProduct();
            orderProduct2.setProduct_variation(buy2);*/


//  ===============================================================================

            Category mobiles = new Category("mobiles");
            categoryRepository.save(mobiles);



        }

    }
}