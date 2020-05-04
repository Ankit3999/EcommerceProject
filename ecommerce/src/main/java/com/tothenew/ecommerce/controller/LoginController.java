package com.tothenew.ecommerce.controller;

import com.tothenew.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    //All working

    @Autowired
    UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @ApiOperation(value = "to logout the currently logged in user")
    @PostMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @ApiOperation(value = "for anonymous user")
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @ApiOperation(value = "if password forgot then user can update it by providing the email")
    @PutMapping("/password/forgot")
    String forgotPassword(@RequestParam("email") String email){
        userService.forgotPassword(email);
        return "token sent to email";
    }

    @ApiOperation(value = "after forgot password user can reset it by providing the token and new password")
    @PutMapping("/password/reset")
    String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password){
        userService.setPassword(token, password);
        return "password reset successful";
    }

}
