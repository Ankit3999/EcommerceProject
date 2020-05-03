package com.tothenew.ecommerce.utilities;

import com.tothenew.ecommerce.services.CurrentUserService;
import com.tothenew.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    CurrentUserService currentUserService;

    @Override
    public Optional<String> getCurrentAuditor() {
        // your custom logic
        Optional<String> currentUser = Optional.empty();
        String principal = currentUserService.getUser();
        currentUser = Optional.of(principal);
        return currentUser;
    }


}