package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.dto.CustomerDto;
import com.tothenew.ecommerce.dto.SellerDto;
import com.tothenew.ecommerce.dto.SellerProfileDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.exception.PatternMismatchException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.AddressRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Seller convtToSeller(SellerDto sellerDto){
        Seller seller =modelMapper.map(sellerDto, Seller.class);
        System.out.println("dto to seller object");
        return seller;
    }

    public SellerProfileDto toSellerViewProfileDto(Seller seller){
        SellerProfileDto sellerProfileDto=modelMapper.map(seller, SellerProfileDto.class);
        return sellerProfileDto;
    }

    public String updateProfile(SellerDto sellerDto){
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
        return "success";
    }

    public String updatePassword(String username, String newPassword) {
        Seller seller=sellerRepository.findByEmail(username);
        seller.setPassword(passwordEncoder.encode(newPassword));
        sellerRepository.save(seller);
        sendMail.sendPasswordResetConfirmationMail(seller.getEmail());
        return "password changed successful";
    }

    public String updateAddress(Long id, AddressDto addressDto, String username) {
        Seller seller=sellerRepository.findByUsername(username);
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

}
