package com.shatbha_shop.shatbha_shop.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.Brand;
import com.shatbha_shop.shatbha_shop.Repositories.BrandRepository;

@Service
public class BrandService {
    
    @Autowired
    private BrandRepository brandRepository;
 
    public List<Brand> GetAllBrands(){
        return brandRepository.findAll();
    }

    public Brand GetBrandByID (String id){
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("الماركه غير موجوده"));
    }

    @SuppressWarnings({"unused" })
    public String AddBrand(Brand brand){
        if(brandRepository.findByName(brand.getName()) != null){
            throw new ConflictException("بيانات الماركه موجوده بالفعل");
        }
        brandRepository.insert(brand); 
        return "Brand Added Successfully";
    }

    public void DeleteBrand(String id){
        brandRepository.deleteById(id);
    }
}

