package com.shatbha_shop.shatbha_shop.Controllers;

import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Services.AuthenticationService;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/api/register")
    public ResponseEntity<SuccessBody> register(
            @RequestBody User user
            ) {
                System.out.println(user);
        return authService.register(user);
    }

    @PostMapping("/api/login")
    public ResponseEntity<SuccessBody> login(
            @RequestBody User user
    ) {
        return authService.authenticate(user);
    }

    @GetMapping("/api/getUserData")
    public ResponseEntity<SuccessBody> getUserData(
            HttpServletRequest request
    ) {
        return authService.getUserData(request);
    }
    
}

