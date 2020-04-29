package com.tothenew.ecommerce.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.RegisteredCustomerDto;
import com.tothenew.ecommerce.dto.RegisteredSellerDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.exception.UserNotFoundException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    SendMail sendMail;
    @Autowired
    MessageSource messageSource;
    @Autowired
    ModelMapper modelMapper;

    public List<RegisteredCustomerDto> getAllRegisteredCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

        List<RegisteredCustomerDto> list = new ArrayList<>();
        for (Long l : userRepository.findIdOfCustomers(paging))
        {
            Optional<User> user1 = userRepository.findById(l);
            User user = user1.get();
            if (user.getId()==l) {
                RegisteredCustomerDto registeredCustomersDTO = modelMapper.map(user,RegisteredCustomerDto.class);
                list.add(registeredCustomersDTO);
            }
        }

        return list;
    }

    public List<RegisteredSellerDto> getAllRegisteredSellers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<RegisteredSellerDto> list = new ArrayList<>();
        for (Long l : userRepository.findIdOfSellers(paging))
        {
            Optional<User> user1 = userRepository.findById(l);
            User user = user1.get();
            if (user.getId()==l)
            {
                RegisteredSellerDto registeredSellersDTO = modelMapper.map(user,RegisteredSellerDto.class);
                AddressDto addressDTO = new AddressDto();
                for (Address address : user.getAddresses())
                {
                    addressDTO = modelMapper.map(address,AddressDto.class);
                }
                registeredSellersDTO.setAddressDTO(addressDTO);
                list.add(registeredSellersDTO);
            }
        }

        return list;
    }
    //fdgdgdfgdgdfgdg

    public ResponseEntity lockUser(Long user_id) {
        User user=null;
        Optional<User> optionalUser=userRepository.findById(user_id);
        if(optionalUser.isPresent()){
            user=optionalUser.get();
            if(user.isLocked()==false)
                return ResponseEntity.ok().body("user account is already locked");
            else{
                user.setLocked(false);
                userRepository.save(user);
                sendMail.sendEmail(user.getEmail(),"regarding account","your account has been locked by admin");
                return ResponseEntity.ok().body("account has been locked");
            }
        }
        else {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        }
    }

    public ResponseEntity unlockUser(Long user_id) {
        User user=null;
        Optional<User> optionalUser=userRepository.findById(user_id);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            if(user.isLocked()==true){
                return ResponseEntity.ok().body("user account is already unlocked");
            }
            else{
                user.setLocked(true);
                userRepository.save(user);
                sendMail.sendEmail(user.getEmail(),"regarding account","your account has been unlocked by admin");
                return ResponseEntity.ok().body("account has been unlocked");
            }
        }
        else {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        }
    }
}
