package com.shatbha_shop.shatbha_shop.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}
