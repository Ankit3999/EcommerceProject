package com.tothenew.ecommerce.bootloader;


import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.repository.*;
import com.tothenew.ecommerce.utilities.Randomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Bootstrap implements ApplicationRunner {
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
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    Randomiser randomiser;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {


       /* for(int i = 1; i<100000; i++){
            Customer customer=new Customer(randomiser.randomAlphaNumeric(), randomiser.randomEmail(),
                    randomiser.randomString(), randomiser.randomString(), randomiser.randomString(),
                    passwordEncoder.encode(randomiser.randomString()),false,true,false,false,true,
                    false,randomiser.randomDate(),randomiser.randomDate(),randomiser.randomString(),randomiser.randomString(), randomiser.randomContact());
            customer.addRole(new Role(1003l, "ROLE_CUSTOMER"));
            customerRepository.save(customer);
        }
*/
        //saving orders

/*        Iterable<Customer> customerList = customerRepository.getTopCustomer();
        for(Customer customer: customerList){
            Orders orders=new Orders(Long.valueOf(randomiser.randomNumber()), randomiser.randomDate(),
                    "COD", randomiser.randomDate(), randomiser.randomDate(),
                    randomiser.randomString(), randomiser.randomString(), customer);
            orders.setOrderAddress(new OrderAddress(randomiser.randomString(), randomiser.randomString(), "India", randomiser.pincode()));

            OrderProduct orderProduct=new OrderProduct(Long.valueOf(randomiser.randomNumber()), orders,
                    randomiser.randomDate(), randomiser.randomDate(), randomiser.randomString(),
                    randomiser.randomString());
            ordersRepository.save(orders);
            orderProductRepository.save(orderProduct);
        }*/


        /*if(userRepository.count()<1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Role admin = new Role(1001l, "ROLE_ADMIN");
            Role seller = new Role(1002l, "ROLE_SELLER");
            Role customer = new Role(1003l, "ROLE_CUSTOMER");
            roleRepository.save(admin);
            roleRepository.save(customer);
            roleRepository.save(seller);

            Admin admin1 = new Admin("ankit12","myemail@ttn.com", "admin", "", "admin");
            admin1.setPassword(passwordEncoder.encode("pass"));
            admin1.setPasswordExpire(false);
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
            customer1.setPasswordExpire(false);
            userRepository.save(customer1);

            *//*Seller seller1 = new Seller("anku12","seller.seller@ttn.com", "seller", "", "seller","bh7ht754r5", "amalgam pvt. lmt.", 9999988817l);
            seller1.setPassword(passwordEncoder.encode("pass"));
            seller1.addRole(seller);
            seller1.addAddress(new Address("kanpur", "UP", "india", "fg95", 2342342l, "home"));
            seller1.setActive(true);

            userRepository.save(seller1);*//*

            Seller seller1=new Seller("ankit", "youremail@ttn.com", "ankit", "", "","3424jk2j4k232e7", "tothenew", 9876378493l);
            userRepository.save(seller1);
            seller1.setPasswordExpire(false);
            System.out.println("Total users saved::"+userRepository.count());



            //products and category



            *//*Category fashion = new Category("fashion");
            Category clothing = new Category("clothing");
            fashion.addSubCategory(clothing);
            Category men = new Category("men");
            Category women = new Category("women");
            clothing.addSubCategory(men);
            clothing.addSubCategory(women);

            categoryRepository.save(fashion);*//*

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

            *//*OrderProduct orderProduct1 = new OrderProduct();
            orderProduct1.setProduct_variation(buy1);

            OrderProduct orderProduct2 = new OrderProduct();
            orderProduct2.setProduct_variation(buy2);*//*


//  ===============================================================================

            Category mobiles = new Category("mobiles");
            categoryRepository.save(mobiles);





            //========================================







        //new data

            Category category = new Category("Clothing");

            Set<Category> categories = new HashSet<>();

            Category category1 = new Category("Men");
            Category category2 = new Category("Women");
            //men
            Category category3 = new Category("Jeans");
            Category category4 = new Category("Shirts");
            Category category5 = new Category("Winter Wear");
            //women
            Category category6 = new Category("Western Wear");
            Category category7 = new Category("Ethnic Wear");
            Category category8 = new Category("Accessories");

            //add to set of categories
            categories.add(category1);
            categories.add(category2);
            categories.add(category3);
            categories.add(category4);
            categories.add(category5);
            categories.add(category6);
            categories.add(category7);
            categories.add(category8);

            category1.setParentCategory(category);
            category2.setParentCategory(category);

            category3.setParentCategory(category1);
            category4.setParentCategory(category1);
            category5.setParentCategory(category1);

            category6.setParentCategory(category2);
            category7.setParentCategory(category2);
            category8.setParentCategory(category2);

            category.setSubCategories(categories);
            category1.setSubCategories(categories);
            category2.setSubCategories(categories);

        Category category9 = new Category("Electronics");

        Set<Category> categories1 = new HashSet<>();

        Category category10 = new Category("Mobile");
        Category category11 = new Category("Laptop");
        //Mobile
        Category category12= new Category("SmartPhones");
        Category category13 = new Category("NormalPhones");
        //Laptop
        Category category15 = new Category("GamingLaptop");
        Category category16 = new Category("NormalLaptop");

        //add to set of categories
        categories1.add(category9);
        categories1.add(category10);
        categories1.add(category12);
        categories1.add(category13);
        categories1.add(category15);
        categories1.add(category16);
        categories1.add(category11);

        category10.setParentCategory(category9);
        category11.setParentCategory(category9);
        category12.setParentCategory(category10);
        category13.setParentCategory(category10);
        category15.setParentCategory(category11);
        category16.setParentCategory(category11);


        category9.setSubCategories(categories1);
        category10.setSubCategories(categories1);
        category11.setSubCategories(categories1);

        categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
            categoryRepository.save(category4);
            categoryRepository.save(category5);
            categoryRepository.save(category6);
            categoryRepository.save(category7);
            categoryRepository.save(category8);
            categoryRepository.save(category9);
            categoryRepository.save(category10);
            categoryRepository.save(category11);
            categoryRepository.save(category12);
            categoryRepository.save(category13);
            categoryRepository.save(category15);
            categoryRepository.save(category16);


            Product product1 = new Product("UCB T-Shirt", "comfortable", "UCB");
            product1.setCategory(category4);
            Product product2 = new Product("RedTape Jeans", "slim fit", "RedTape");
            product1.setCategory(category3);
            Product product3 = new Product("Nike shoes", "light weight", "Nike");
            product1.setCategory(category8);

        ///metadata

            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
            categoryMetadataField.setName("SIZE");
            categoryMetadataFieldRepository.save(categoryMetadataField);


            CategoryMetadataField categoryMetadataField1 = new CategoryMetadataField();
            categoryMetadataField1.setName("COLOR");
            categoryMetadataFieldRepository.save(categoryMetadataField1);

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId.setCategoryId(category4.getId());
            categoryMetadataFieldValuesId.setCategoryMetadataFieldId(categoryMetadataField.getId());

            CategoryMetadataFieldValuesId categoryMetadataFieldValuesId1= new CategoryMetadataFieldValuesId();
            categoryMetadataFieldValuesId1.setCategoryId(category4.getId());
            categoryMetadataFieldValuesId1.setCategoryMetadataFieldId(categoryMetadataField1.getId());


            CategoryMetadataFieldValues categoryMetadataFieldValues= new CategoryMetadataFieldValues();
            categoryMetadataFieldValues.setFieldValues("L,M,S");
            categoryMetadataFieldValues.setId(categoryMetadataFieldValuesId);
            categoryMetadataFieldValues.setCategoryMetadataField(categoryMetadataField);
            categoryMetadataFieldValues.setCategory(category4);

            CategoryMetadataFieldValues categoryMetadataFieldValues1= new CategoryMetadataFieldValues();
            categoryMetadataFieldValues1.setFieldValues("BLACK,BLUE");
            categoryMetadataFieldValues1.setId(categoryMetadataFieldValuesId1);
            categoryMetadataFieldValues1.setCategoryMetadataField(categoryMetadataField1);
            categoryMetadataFieldValues1.setCategory(category4);


            Set<CategoryMetadataFieldValues> categoryMetadataFieldValues2 = new HashSet<>();
            categoryMetadataFieldValues2.add(categoryMetadataFieldValues);
            categoryMetadataFieldValues2.add(categoryMetadataFieldValues1);
            category4.setFieldValues(categoryMetadataFieldValues2);


            productRepository.save(product1);
            *//*categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);
            categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues1);
            *//*productRepository.save(product2);
            productRepository.save(product3);

            //product1.setCategory(men);
            product1.addVariation(mSize);
            product1.addVariation(lSize);

            //seller1.addProduct(product1);

            productRepository.save(product1);


            ProductReview productReview=new ProductReview();
            productReview.setId("001w");
            productReview.setRating("good");
            productReview.setReview("its a good product must buy");
            productReview.setCustomerId(25l);
            productReview.setProductId(1002l);
            productReviewRepository.save(productReview);

            System.out.println("\n\n\n***the application went up***");
        }*/

    }
}