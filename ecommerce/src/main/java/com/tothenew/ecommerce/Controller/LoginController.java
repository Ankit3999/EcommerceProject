package com.tothenew.ecommerce.Controller;

import com.tothenew.ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/password/forgot")
    String forgotPassword(@RequestParam("email") String email){
        userService.forgotPassword(email);
        return "token sent to email";
    }

    @GetMapping("/password/reset")
    String resetPassword(@RequestParam("token") String token, @RequestParam("password") String password){
        userService.setPassword(token, password);
        return "password reset successful";
    }

}
