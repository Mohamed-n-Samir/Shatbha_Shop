package com.shatbha_shop.shatbha_shop.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Repositories.UserRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    List<User> result = new ArrayList<User>();

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    public User GetUserByID(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("المستخدم غير موجود"));
    }

    public ResponseEntity<SuccessBody> AddUser(User user) {
        if (userRepository.findByEmailOrMobile(user.getEmail(), user.getMobile()) != null) {
            throw new ConflictException("بيانات المستخدم موجوده بالفعل");
        }
        userRepository.insert(user);
        SuccessBody body = new SuccessBody("Register Success ! login now", "/api/register");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public void DeleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
