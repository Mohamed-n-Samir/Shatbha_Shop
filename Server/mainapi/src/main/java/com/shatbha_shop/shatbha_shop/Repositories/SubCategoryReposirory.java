package com.shatbha_shop.shatbha_shop.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Models.SubCategory;

public interface SubCategoryReposirory extends MongoRepository<SubCategory,String>{

    List<SubCategory> findAllByCategory(PCategory category);
} 