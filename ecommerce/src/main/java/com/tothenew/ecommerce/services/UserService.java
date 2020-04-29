package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.AddressDto;
import com.tothenew.ecommerce.entity.Address;
import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.Token;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.exception.NotFoundException;
import com.tothenew.ecommerce.exception.NullException;
import com.tothenew.ecommerce.exception.UserNotFoundException;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.AddressRepository;
import com.tothenew.ecommerce.repository.CustomerRepository;
import com.tothenew.ecommerce.repository.TokenRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    public int count;
    Long[] l = {};
    @Autowired
    UserRepository userRepository;

    private JavaMailSender javaMailSender;

    @Autowired
    TokenService tokenService;
    @Autowired
    SendMail sendMail;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    MailVerification mailVerification;
    @Autowired
    MessageSource messageSource;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressService addressService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity activateUser(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.getActive()==true)
            {
                return ResponseEntity.ok().body("user account is already deactivated");
            }
            else
            {
                user1.setActive(true);
                System.out.println("Sending email for account activation");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getEmail());
                mail.setFrom("kumsag11@gmail.com");
                mail.setSubject("Regarding account activation");
                mail.setText("your account has been activated by admin you can now login");
                System.out.println("now starting");
                javaMailSender.send(mail);
                userRepository.save(user1);
                System.out.println("Email Sent!");
                return ResponseEntity.ok().body("account has been successfully deactivated");
            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        }
    }

    @Async
    public ResponseEntity deActivateuser(Long id)
    {
        User user1 = null;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
        {
            user1 = user.get();
            if (user1.getActive()==false)
            {
                return ResponseEntity.ok().body("user account is already deactivated");
            }
            else
            {
                user1.setActive(false);
                userRepository.save(user1);
                System.out.println("Sending email...");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getEmail());
                mail.setFrom("kumsag11@gmail.com");
                mail.setSubject("Regarding account deactivation");
                mail.setText("your account has been deactivated by admin you can not login now");
                javaMailSender.send(mail);
                System.out.println("Email Sent!");
                return ResponseEntity.ok().body("account has been successfully deactivated");            }
        }
        else
        {
            Long[] l ={};
            throw new UserNotFoundException(messageSource.getMessage("message3.txt",l, LocaleContextHolder.getLocale()));
        }
    }

    public String addAddress(AddressDto addressDto){
        String email=currentUserService.getUser();
        User user=userRepository.findByEmail(email);
        Address address=addressService.toAddress(addressDto);
        address.setUser(user);
        user.addAddress(address);
        userRepository.save(user);
        return "Address saved";
    }


    @Modifying
    @Transactional
    public String deleteAddress(Long id) {
        String username=currentUserService.getUser();
        Optional<Address> addressOptional=addressRepository.findById(id);
        if(!addressOptional.isPresent()){
            throw new NotFoundException("address not present");
        }
        Address savedAddress = addressOptional.get();
        if(savedAddress.getUser().equals(username)){
            addressRepository.deleteAddressById(id);
            return "address deleted";
        }
        return "profile is updated";
    }

    @Modifying
    @Transactional
    public void updateAddress(Long id, AddressDto address) {
        String email=currentUserService.getUser();
        User user=userRepository.findByEmail(email);
        Set<Address> addresses=user.getAddresses();
        Optional<Address> address1 = addressRepository.findById(id);
        int count=0;
        if(!address1.isPresent()){
            throw new NotFoundException(messageSource.getMessage("message8.txt",l, LocaleContextHolder.getLocale()));
        }
        else{
            Address savedAddress2 = address1.get();
            for (Address address2 : addresses) {
                if (address1.get().getId() == address2.getId()) {
                    if (address.getAddressLine() != null)
                        address2.setAddressLine(address.getAddressLine());
                    if (address.getCity() != null)
                        address2.setCity(address.getCity());
                    if (address.getCountry() != null)
                        address2.setCountry(address.getCountry());
                    if (address.getState() != null)
                        address2.setState(address.getState());
                    if (address.getZipCode() != null)
                        address2.setZipCode(address.getZipCode());
                    address2.setUser(user);
                    address2.setId(id);
                    addressRepository.save(address2);
                    count++;
                }
            }
            if (count==0)
            {
                throw new NullException("you cannot update this address");
            }
        }
    }

    public List<AddressDto> getAddress(){
        Long[] l = {};
        String email = currentUserService.getUser();
        Customer customer = customerRepository.findByEmail(email);
        Set<Address> addresses = customer.getAddresses();
        List<AddressDto> list = new ArrayList<>();
        if (addresses.isEmpty())
        {
            throw new NotFoundException(messageSource.getMessage("message8.txt",l,LocaleContextHolder.getLocale()));
        }
        else
        {
            for (Address address : addresses)
            {
                AddressDto addressDTO = modelMapper.map(address,AddressDto.class);
                list.add(addressDTO);

            }

        }
        return list;
    }

    public String updatePassword(String newPassword) {
        String username=currentUserService.getUser();
        User user=userRepository.findByEmail(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        sendMail.sendPasswordResetConfirmationMail(user.getEmail());
        return "password changed successful";
    }

    public void forgotPassword(String email_id) {
        User user = userRepository.findByEmail(email_id);
        if (user == null) {
            System.out.println("no user found with this email id");
            throw new RuntimeException();
        } else {
            System.out.println("Sending email...");
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setFrom("kumsag11@gmail.com");
            mail.setSubject("Regarding forgot password");
            String uu = tokenService.getToken(user);
            mail.setText(uu);
            javaMailSender.send(mail);
            System.out.println("Email Sent!");
        }
    }

    public void setPassword(String token_on_mail, String password) {
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(token_on_mail)) {
                token1 = token;
            }
        }
        if (token1 == null) {
            System.out.println("invalid token");
        } else {
            if (token1.isExpired()) {
                mailVerification.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
            } else {
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setPassword(new BCryptPasswordEncoder().encode(password));
                userRepository.save(user2);
                tokenRepository.delete(token1);
            }
        }
    }

}
