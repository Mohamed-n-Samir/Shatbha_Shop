package com.shatbha_shop.shatbha_shop.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.WrongTokenException;
import com.shatbha_shop.shatbha_shop.Models.Token;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Repositories.TokenRepository;
import com.shatbha_shop.shatbha_shop.Repositories.UserRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<SuccessBody> register(@Valid User user) {

        if (repository.findByEmailOrMobile(user.getEmail(), user.getMobile()) != null) {
            throw new ConflictException("بيانات المستخدم موجوده بالفعل");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("user");

        repository.insert(user);

        SuccessBody body = new SuccessBody("Register Success ! login now", "/api/register");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = repository.findByEmail(request.getUsername()).orElseThrow();

        Map<String,String> userRes = new HashMap<>();

        userRes.put("id", user.getId());
        userRes.put("firstname", user.getFirstname());
        userRes.put("lastname", user.getLastname());
        userRes.put("email", user.getEmail());
        userRes.put("mobile", user.getMobile());
        userRes.put("role", user.getRole());
        userRes.put("city", user.getCity().getId());
        userRes.put("area", user.getArea());
        userRes.put("buildingAndApartment", user.getBuildingAndApartment());
        userRes.put("gender", user.getGender());

        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", jwt);
        tokenMap.put("user",userRes);

        SuccessBody body = new SuccessBody("تم تسجيل الدخول بنجاح", "/api/login", tokenMap);

        return new ResponseEntity<SuccessBody>(body,HttpStatus.ACCEPTED);

    }

    public ResponseEntity<SuccessBody> getUserData(HttpServletRequest request) throws WrongTokenException {

        String email = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        User user = repository.findByEmail(email).orElseThrow(()->new NotFoundException("User Not Found"));

        Map<String,Object> userRes = new HashMap<>();

        userRes.put("id", user.getId());
        userRes.put("firstname", user.getFirstname());
        userRes.put("lastname", user.getLastname());
        userRes.put("email", user.getEmail());
        userRes.put("mobile", user.getMobile());
        userRes.put("role", user.getRole());
        userRes.put("city", user.getCity());
        userRes.put("area", user.getArea());
        userRes.put("buildingAndApartment", user.getBuildingAndApartment());
        userRes.put("gender", user.getGender());

        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        Map<String, Object> userObj = new HashMap<>();
        userObj.put("user",userRes);

        SuccessBody body = new SuccessBody("User Data Getted", "/api/getUserData",userObj);

        return new ResponseEntity<SuccessBody>(body,HttpStatus.ACCEPTED);

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllByUserIdAndLoggedOutFalse(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
