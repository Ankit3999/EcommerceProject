package com.tothenew.ecommerce.DtOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDto extends UserDto{
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    Long contact;

    public CustomerDto(String email, String username, String firstName, String middleName, String lastName, String password, String confirmPassword, Long contact){
        super(email, username, firstName, middleName, lastName, password, confirmPassword);
        this.contact=contact;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}
