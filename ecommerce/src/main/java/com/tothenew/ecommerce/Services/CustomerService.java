package com.tothenew.ecommerce.Services;

import com.tothenew.ecommerce.DtOs.CustomerDto;
import com.tothenew.ecommerce.DtOs.UserDto;
import com.tothenew.ecommerce.Entity.Customer;
import com.tothenew.ecommerce.Repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;

    public Customer convtToCustomer(CustomerDto customerDto){
        Customer customer=modelMapper.map(customerDto, Customer.class);
        System.out.println("dto to customer object");
        return customer;
    }
}
