package com.tothenew.ecommerce.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
@Component
public class CustomerProfileDto extends UserProfileDto{
    @Size(min = 10, max = 10, message = "invalid phone number")
    private Long contact;

    public CustomerProfileDto() {
    }

    public CustomerProfileDto(Long id, String firstName, String lastName, Boolean isActive, String image, Long contact) {
        super(id, firstName, lastName, isActive, image);
        this.contact=contact;
    }
}
