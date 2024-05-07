package com.orderservice.orderservice.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderservice.orderservice.Model.Product;

public interface ProductRepository extends MongoRepository <Product,String>{

    
}
