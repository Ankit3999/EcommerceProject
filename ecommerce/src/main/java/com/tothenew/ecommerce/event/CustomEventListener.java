/*
package com.tothenew.ecommerce.event;

import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.entity.UserAttempts;
import com.tothenew.ecommerce.exception.NotFoundException;
import com.tothenew.ecommerce.exception.PasswordExpiredException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.UserAttemptsRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import com.tothenew.ecommerce.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Component
public class CustomEventListener {
    @Autowired
    UserAttemptsRepository userAttemptsRepository;
    @Autowired
    UserRepository userRepository;
    @Lazy
    @Autowired
    SendMail sendMail;
    @Autowired
    MessageSource messageSource;
    @Autowired
    LoginService loginService;


    @EventListener
    public void AuthenticationFailEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = event.getAuthentication().getPrincipal().toString();
        Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();
        int count=0;
            for (UserAttempts userAttempts1 : userAttempts) {
                if (userAttempts1.getEmail().equals(email)) {
                    if (userAttempts1.getAttempts()>=3) {
                        User user = userRepository.findByEmail(email);
                        user.setAccountNonLocked(false);
                        userRepository.save(user);
                        count++;
                        sendMail.sendEmail(user.getEmail(), "Regarding account", "your account has been locked");
                    }
                    else {
                        userAttempts1.setAttempts(userAttempts1.getAttempts() + 1);
                        userAttemptsRepository.save(userAttempts1);
                        count++;
                    }
                }
            }
        if (count==0) {
            UserAttempts userAttempts1 = new UserAttempts();
            User user = userRepository.findByEmail(email);
            userAttempts1.setEmail(user.getEmail());
            userAttempts1.setAttempts(1);
            userAttemptsRepository.save(userAttempts1);
        }
    }

    @EventListener
    public void AuthenticationPass(AuthenticationSuccessEvent event) {
        String email = event.getAuthentication().getPrincipal().toString();
        User user=userRepository.findByEmail(email);
        if(!user.isPasswordExpire()) {
            try {
                LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
                Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();
                for (UserAttempts userAttempts1 : userAttempts) {
                    if (userAttempts1.getEmail().equals(hashMap.get("username"))) {
                        userAttemptsRepository.deleteById(userAttempts1.getId());
                    }
                }
            } catch (Exception e) { }
        }
        else{
            Long[] l = {};
            throw  new PasswordExpiredException(messageSource.getMessage("message23.txt",l, LocaleContextHolder.getLocale()));

        }
    }

}
*/
