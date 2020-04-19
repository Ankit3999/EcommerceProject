package com.tothenew.ecommerce.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;


    public MappingJacksonValue registeredCustomers(String page, String size, String SortBy){
        List<Customer> customers = customerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","firstName","lastName","email","active");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("CustomerFilter",filter);

        MappingJacksonValue message = new MappingJacksonValue(customers);

        message.setFilters(filterProvider);
        return message;
    }


    //ALERT EXPLICITLY CASTED TO PAGEABLE

    public MappingJacksonValue registeredSellers(String page,String size, String SortBy){
        List<Seller> sellers = sellerRepository.findAll( PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","firstName","lastName","email","active","companyName","companyContact","addresses");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("Seller-Filter",filter);

        MappingJacksonValue message = new MappingJacksonValue(sellers);

        message.setFilters(filterProvider);
        return message;
    }

}
