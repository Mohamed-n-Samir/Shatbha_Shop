package com.shatbha_shop.shatbha_shop.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.PCategory;

public interface PCategoryRepository  extends MongoRepository<PCategory, String>{
    PCategory findByTitle(String title);
    
}


