/*
package com.tothenew.ecommerce.event;

import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.entity.UserAttempts;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.UserAttemptsRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class CustomEventListener
{
    @Autowired
    UserAttemptsRepository userAttemptsRepository;

    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    SendMail sendMail;

    @EventListener
    public void AuthenticationFailEvent(AuthenticationFailureBadCredentialsEvent event)
    {
        String username = event.getAuthentication().getPrincipal().toString();
        Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();
        int count=0;
        for (UserAttempts userAttempts1 : userAttempts)
        {
            if (userAttempts1.getEmail().equals(username))
            {
                if (userAttempts1.getAttempts()>=3)
                {
                    User user = userRepository.findByUsername(username);
                    user.setLocked(true);
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
        if (count==0)
        {
            UserAttempts userAttempts1 = new UserAttempts();
            User user = userRepository.findByUsername(username);
            userAttempts1.setEmail(user.getUsername());
            userAttempts1.setAttempts(1);
            userAttemptsRepository.save(userAttempts1);
        }
    }

    @EventListener
    public void AuthenticationPass(AuthenticationSuccessEvent event)
    {
        try {
            LinkedHashMap<String ,String > hashMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
            Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();

            for (UserAttempts userAttempts1 : userAttempts)
            {
                if (userAttempts1.getEmail().equals(hashMap.get("username")))
                {
                    userAttemptsRepository.deleteById(userAttempts1.getId());
                }
            }
        }
        catch (Exception e)
        {

        }
    }
}*/
