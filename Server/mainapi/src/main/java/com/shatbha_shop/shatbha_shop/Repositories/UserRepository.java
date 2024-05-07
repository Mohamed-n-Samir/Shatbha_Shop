package com.shatbha_shop.shatbha_shop.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.User;

public interface UserRepository extends MongoRepository<User, String> {
    
    User findByEmailOrMobile(String email,String mobile);
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(String role);
    

}
