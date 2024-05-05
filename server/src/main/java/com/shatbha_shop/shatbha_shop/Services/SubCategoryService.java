package com.shatbha_shop.shatbha_shop.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Models.SubCategory;
import com.shatbha_shop.shatbha_shop.Repositories.SubCategoryReposirory;

@Service
public class SubCategoryService {
    
    @Autowired
    private SubCategoryReposirory subCategoryReposirory; 

    public List<SubCategory> getAllSubCategories(){
        return subCategoryReposirory.findAll();
    }
}
