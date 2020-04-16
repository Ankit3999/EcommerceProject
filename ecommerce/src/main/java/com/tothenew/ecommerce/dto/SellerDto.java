package com.tothenew.ecommerce.dto;

import com.tothenew.ecommerce.validator.ValidGST;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Component
public class SellerDto extends UserDto {
    @NotNull
    @NotEmpty
    @Size(min = 15, max = 15)
    @ValidGST
    private Long gst;
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    private Long companyContact;
    @NotNull
    @NotEmpty
    private String companyName;

    public SellerDto() {
    }

    public SellerDto(String email, String username, String firstName, String middleName, String lastName, String password, String confirmPassword, Long gst, Long companyContact, String companyName){
        super(email, username, firstName, middleName, lastName, password, confirmPassword);
        this.gst=gst;
        this.companyContact=companyContact;
        this.companyName=companyName;
    }

    public Long getGst() {
        return gst;
    }

    public void setGst(Long gst) {
        this.gst = gst;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
