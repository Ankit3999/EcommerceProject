package com.tothenew.ecommerce.Services;

import com.tothenew.ecommerce.DtOs.CustomerDto;
import com.tothenew.ecommerce.DtOs.SellerDto;
import com.tothenew.ecommerce.Entity.Customer;
import com.tothenew.ecommerce.Entity.Seller;
import com.tothenew.ecommerce.Repository.SellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ModelMapper modelMapper;

    public Seller convtToSeller(SellerDto sellerDto){
        Seller seller =modelMapper.map(sellerDto, Seller.class);
        System.out.println("dto to seller object");
        return seller;
    }

}
