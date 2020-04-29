package com.tothenew.ecommerce.services;


import com.tothenew.ecommerce.entity.Token;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.exception.TokenNotFoundException;
import com.tothenew.ecommerce.mailing.MailVerification;
import com.tothenew.ecommerce.repository.TokenRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailVerification mailVerification;
    public String getToken(User user){
        Token token = new Token();
        String uu = UUID.randomUUID().toString();
        token.setRandomToken(uu);
        token.setTimeInMill(System.currentTimeMillis());
        token.setName(user.getUsername());
        tokenRepository.save(token);
        return uu;
    }

    public void verifyToken(String u) {
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(u)) {
                token1 = token;
            }
        }
        if (token1 == null)
        {
            throw new TokenNotFoundException("token is invalid");
        } else {
            if (token1.isExpired())
            {
                mailVerification.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
            } else {
                System.out.println("saving");
                User user = userRepository.findByUsername(token1.getName());
                user.setActive(true);
                System.out.println(user.getUsername() + " " + user.getActive());
                userRepository.save(user);
                tokenRepository.delete(token1);
            }
        }
    }
}
