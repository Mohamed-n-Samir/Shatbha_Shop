package com.shatbha_shop.shatbha_shop.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.BaseException;
import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Repositories.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    List<User> result = new ArrayList<User>();
 
    public List<User> GetAllUsers(){
        return userRepository.findAll();
    }

    @SuppressWarnings("null")
    public User GetUserByID (String id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("المستخدم غير موجود"));
    }

    // @SuppressWarnings("null")
    public String AddUser(User user){
        if(userRepository.findByEmailOrMobile(user.getEmail(), user.getMobile()) != null){
            throw new ConflictException("بيانات المستخدم موجوده بالفعل");
        }
        userRepository.insert(user);
        // throw new CustomException("What is the Fuck"); 
        return "User Added Successfully";
    }

    @SuppressWarnings("null")
    public void DeleteUser(String id){
        userRepository.deleteById(id);
    }
}
