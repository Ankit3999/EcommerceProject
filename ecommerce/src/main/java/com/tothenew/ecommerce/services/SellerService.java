package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.*;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.exception.PatternMismatchException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.AddressRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SendMail sendMail;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    public Seller convtToSeller(SellerDto sellerDto){
        Seller seller =modelMapper.map(sellerDto, Seller.class);
        System.out.println("dto to seller object");
        return seller;
    }

    public SellerProfileDto toSellerViewProfileDto(Seller seller){
        SellerProfileDto sellerProfileDto=modelMapper.map(seller, SellerProfileDto.class);
        return sellerProfileDto;
    }

    public SellerProfileDto viewProfile(){
        String username=currentUserService.getUser();
        Seller seller= sellerRepository.findByEmail(username);
        return toSellerViewProfileDto(seller);
    }

    public ResponseEntity updateProfile(SellerDto sellerDto){
        String username=currentUserService.getUser();
        Seller seller=sellerRepository.findByEmail(username);
        if (sellerDto.getFirstName()!=null)
            seller.setFirstName(sellerDto.getFirstName());
        if (sellerDto.getMiddleName()!=null)
            seller.setMiddleName(sellerDto.getMiddleName());
        if (sellerDto.getLastName()!=null)
            seller.setLastName(sellerDto.getLastName());
        if (sellerDto.getCompanyContact()!=null)
        {
            if (sellerDto.getCompanyContact().toString().matches("(\\+91|0)[0-9]{10}"))
            {
                seller.setCompanyContact(sellerDto.getCompanyContact());
            }
            else
            {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
        if(sellerDto.getCompanyName()!=null)
            seller.setCompanyName(sellerDto.getCompanyName());
        if(sellerDto.getGst()!=null)
            seller.setGst(sellerDto.getGst());
        sellerRepository.save(seller);
        return ResponseEntity.ok().body("profile is updated successfully");
    }

}
