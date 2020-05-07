package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.Criteria.Criteria;
import com.tothenew.ecommerce.dto.RegisteredCustomerDto;
import com.tothenew.ecommerce.dto.RegisteredSellerDto;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.services.AdminService;
import com.tothenew.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AdminController {
    //All working

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    Criteria criteria;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "home page for admin")
    @GetMapping("/admin/home")
    public ResponseEntity adminHome(){
        String msg= "Admin home";
        return new ResponseEntity(msg, HttpStatus.OK);
    }

    @ApiOperation(value = "uri in which admin can view all the registered customers")
    @GetMapping("/admin/listAllCustomers")
    public List<RegisteredCustomerDto> getAllCustomers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                       @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                       @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return adminService.getAllRegisteredCustomers(pageNo, pageSize, sortBy);
    }

    @ApiOperation(value = "uri in which admin can view all the registered sellers")
    @GetMapping("/admin/listAllSellers")
    public List<RegisteredSellerDto> getAllSellers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                   @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        return adminService.getAllRegisteredSellers(pageNo, pageSize, sortBy);
    }


    @ApiOperation(value = "uri in which admin can activate a user")
    @GetMapping("/admin/activate")
    public ResponseEntity activateCustomer(@RequestParam("id") Long id){
        return userService.activateUser(id);
    }

    @ApiOperation(value = "uri in which admin can deactivate a user")
    @GetMapping("/admin/deactivate")
    public ResponseEntity deactivateCustomer(@RequestParam("id") Long id){ return userService.deActivateuser(id); }

    @ApiOperation(value = "uri in which admin can lock an user account")
    @PutMapping("/admin/lock")
    public ResponseEntity lockUser(@RequestParam("id") Long id) { return adminService.lockUser(id); }

    @ApiOperation(value = "uri in which admin can unlock an user account")
    @PutMapping("/admin/unlock")
    public ResponseEntity unlockUser(@RequestParam("id") Long id) { return adminService.unlockUser(id); }

    @ApiOperation(value = "get a customer using criteria query")
    @GetMapping("/admin/userget")
    public User getProfile(@RequestParam("email") String email){
        return criteria.findByEmail(email);
    }

}
