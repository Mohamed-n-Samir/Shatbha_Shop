package com.shatbha_shop.shatbha_shop.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.shatbha_shop.shatbha_shop.Exceptions.WrongTokenException;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {

    @Pointcut("execution(* com.shatbha_shop.shatbha_shop.security.config.CustomLogoutHandler.*(..))")
    public void authInLogout() {

    }

    @Pointcut("execution(* com.shatbha_shop.shatbha_shop.Services.UserService.*(..))")
    public void authInUser() {

    }

    @Pointcut("execution(* com.shatbha_shop.shatbha_shop.Services.AuthenticationService.*(..))")
    public void authInGetData() {

    }

    // "execution(*
    // com.shatbha_shop.shatbha_shop.Services.AuthenticationService.*(..))"
    @Before("authInLogout() || authInGetData() || authInUser()")
    public void authenticate(JoinPoint joinPoint) {
        HttpServletRequest req;
        try {
            req = (HttpServletRequest) joinPoint.getArgs()[0];

        } catch (Exception e) {
           return;
        }
        String authHeader = req.getHeader("Authorization");
        System.out.println("\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(authHeader);
            throw new WrongTokenException("Token not found or wrong");
        }

        System.out.println(authHeader);
    }

}
