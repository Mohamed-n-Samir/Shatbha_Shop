package com.shatbha_shop.shatbha_shop.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByNewPriceGreaterThan(double newPrice);
    Optional<Product> findBySlug(String slug);
}
