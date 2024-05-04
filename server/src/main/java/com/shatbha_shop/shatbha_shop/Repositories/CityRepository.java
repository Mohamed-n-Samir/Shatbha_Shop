package com.shatbha_shop.shatbha_shop.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.City;

public interface CityRepository extends MongoRepository<City, String> {
    
    City findByName(String Name);
}
