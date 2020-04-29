package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.exception.PatternMismatchException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.AddressRepository;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressService addressService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    SendMail sendMail;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageSource messageSource;

    public Customer convtToCustomer(CustomerDto customerDto){
        Customer customer=modelMapper.map(customerDto, Customer.class);
        System.out.println("dto to customer object");
        return customer;
    }

    public CustomerDto convtToCustomerDto(Customer customer){
        CustomerDto customerDto=modelMapper.map(customer, CustomerDto.class);
        System.out.printf("customer to dto object");
        return customerDto;
    }

    public CustomerProfileDto toCustomerViewProfileDto(Customer customer){
        CustomerProfileDto customerViewProfileDto = modelMapper.map(customer, CustomerProfileDto.class);
        return customerViewProfileDto;
    }

    public CustomerProfileDto viewProfile() {
        String username=currentUserService.getUser();
        Customer customer= customerRepository.findByEmail(username);
        return toCustomerViewProfileDto(customer);
    }

    public String updateProfile(CustomerDto customer){
        String username=currentUserService.getUser();
        Customer customer1=customerRepository.findByEmail(username);
        if (customer.getFirstName()!=null)
            customer1.setFirstName(customer.getFirstName());
        if (customer.getMiddleName()!=null)
            customer1.setMiddleName(customer.getMiddleName());
        if (customer.getLastName()!=null)
            customer1.setLastName(customer.getLastName());
        if (customer.getContact()!=null)
        {
            if (customer.getContact().toString().matches("(\\+91|0)[0-9]{10}"))
            {
                customer1.setContact(customer.getContact());
            }
            else
            {
                Long[] l = {};
                throw new PatternMismatchException(messageSource.getMessage("message5.txt",l, LocaleContextHolder.getLocale()));
            }
        }
        customerRepository.save(customer1);
        return "success";
    }

}
