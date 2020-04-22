package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.CustomerProfileDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.AddressRepository;
import com.tothenew.ecommerce.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AddressService addressService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    SendMail sendMail;

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

    public String addAddress(Customer customer, AddressDto addressDto){
        Address address=addressService.toAddress(addressDto);
        customer.addAddress(address);
        customerRepository.save(customer);
        return "Address saved";
    }

    public String deleteAddress(Long id, String username) {
        Optional<Address> addressOptional=addressRepository.findById(id);
        if(!addressOptional.isPresent()){
            return "address not found";
        }
        Address savedAddress = addressOptional.get();
        if(savedAddress.getUser().equals(username)){
            addressRepository.deleteAddressById(id);
            return "address deleted";
        }
        return "profile is updated";
    }

    public String updateAddress(Long id, AddressDto addressDto, String username) {
        Customer customer=customerRepository.findByUsername(username);
        Optional<Address> address = addressRepository.findById(id);
        if(!address.isPresent()){
            return "address not found";
        }
        Address savedAddress = address.get();


        if(addressDto.getAddressLine() != null)
            savedAddress.setAddressLine(addressDto.getAddressLine());

        if(addressDto.getCity() != null)
            savedAddress.setCity(addressDto.getCity());

        if(addressDto.getState() != null)
            savedAddress.setState(addressDto.getState());

        if(addressDto.getCountry() != null)
            savedAddress.setCountry(addressDto.getCountry());

        if(addressDto.getZipCode() != null)
            savedAddress.setZipCode(addressDto.getZipCode());

        if(addressDto.getLabel() != null)
            savedAddress.setLabel(addressDto.getLabel());
        return "address updated successfully";
    }

    public String updatePassword(String username, String newPassword) {
        Customer customer=customerRepository.findByEmail(username);
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
        sendMail.sendPasswordResetConfirmationMail(customer.getEmail());
        return "password changed successful";
    }
}
