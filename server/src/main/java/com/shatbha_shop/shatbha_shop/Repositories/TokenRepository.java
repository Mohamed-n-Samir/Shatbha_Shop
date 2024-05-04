package com.shatbha_shop.shatbha_shop.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findAllByUserIdAndLoggedOutFalse(String userId);

    Optional<Token> findByToken(String token);
}