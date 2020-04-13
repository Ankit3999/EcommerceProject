package com.tothenew.ecommerce.Controller;

import com.tothenew.ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/forgotpassword")
    String forgotPassword(@RequestParam("email") String email){
        userService.forgotPassword(email);
        return "token sent to email";
    }

    @GetMapping("/resetpassword")
    String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password){
        userService.setPassword(token, password);
        return "password reset successful";
    }

}
