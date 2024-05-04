package com.shatbha_shop.shatbha_shop.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Repositories.PCategoryRepository;

@Service
public class PCategoryService {
    
    @Autowired
    private PCategoryRepository pCategoryRepository;
 
    public List<PCategory> GetAllPCategorys(){
        return pCategoryRepository.findAll();
    }

    public PCategory GetPCategoryByID (String id){
        return pCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("aa غير موجوده"));
    }

    @SuppressWarnings({"unused" })
    public String AddPCategory(PCategory pCategory){
        if(pCategoryRepository.findByTitle(pCategory.getTitle()) != null){
            throw new ConflictException("بيانات aa موجوده بالفعل");
        }
        pCategoryRepository.insert(pCategory); 
        return "PCategory Added Successfully";
    }

    public void DeletePCategory(String id){
        pCategoryRepository.deleteById(id);
    }
}

