package com.shatbha_shop.shatbha_shop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.Brand;
import com.shatbha_shop.shatbha_shop.Services.BrandService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/api/getAllBrands")
    public List<Brand> GetAllBrands() {
        return brandService.GetAllBrands();
    }

    @PostMapping("/api/addBrand")
    public String AddBrand(@RequestBody @Valid Brand brand) {
        return brandService.AddBrand(brand);
    }
    
}

