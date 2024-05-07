package com.shatbha_shop.shatbha_shop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.SubCategory;
import com.shatbha_shop.shatbha_shop.Services.SubCategoryService;

import java.util.List;

@RestController
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/api/getAllSubCategoryData")
    public List<SubCategory> getAllSubCategories(){
        return subCategoryService.getAllSubCategoriesData();
    }
    
}
