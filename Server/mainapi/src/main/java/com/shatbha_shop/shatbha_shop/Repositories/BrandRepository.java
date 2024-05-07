package com.shatbha_shop.shatbha_shop.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.Brand;

public interface BrandRepository extends MongoRepository<Brand, String> {
    Brand findByName(String name);

}
