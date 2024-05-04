package com.shatbha_shop.shatbha_shop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Services.PCategoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("")
public class PCategoryController {

    @Autowired
    private PCategoryService pCategoryService;

    @GetMapping("/api/getAllPCategorys")
    public List<PCategory> GetAllPCategorys() {
        return pCategoryService.GetAllPCategorys();
    }

    @PostMapping("/api/addPCategory")
    public String AddPCategory(@RequestBody @Valid PCategory pCategory) {
        return pCategoryService.AddPCategory(pCategory);
    }
    
}


