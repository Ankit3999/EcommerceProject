package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.AdminService;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/home")
    public ResponseEntity adminHome(){
        String msg= "Admin home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @GetMapping("/admin/customer")
    public MappingJacksonValue showcustomer(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "id") String SortBy){
        return adminService.registeredCustomers(page, size, SortBy);
    }

    @GetMapping("/admin/seller")
    public MappingJacksonValue showseller(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "id") String SortBy){
        return adminService.registeredSellers(page, size, SortBy);
    }

    @GetMapping("/admin/activate")
    public String activateCustomer(@RequestParam("id") Long id){
        userService.activateUser(id);
        return "this user is activated";
    }

    @GetMapping("/admin/deactivate")
    public void deactivateCustomer(@RequestParam("id") Long id){
        userService.deActivateuser(id);
    }

}
