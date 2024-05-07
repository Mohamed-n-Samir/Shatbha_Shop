package com.shatbha_shop.shatbha_shop.Services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.SomethingWentWrong;
import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Repositories.PCategoryRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class PCategoryService {

    @Autowired
    private PCategoryRepository pCategoryRepository;

    public List<PCategory> GetAllPCategorys() {
        return pCategoryRepository.findAll();
    }

    public PCategory GetPCategoryByID(String id) {
        return pCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("aa غير موجوده"));
    }

    public String AddPCategory(PCategory pCategory) {
        if (pCategoryRepository.findByTitle(pCategory.getTitle()) != null) {
            throw new ConflictException("بيانات aa موجوده بالفعل");
        }
        pCategoryRepository.insert(pCategory);
        return "PCategory Added Successfully";
    }

    public ResponseEntity<SuccessBody> updateCategoryByFields(String Id, Map<String, Object> fields) {

        PCategory existingCategory = pCategoryRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(PCategory.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingCategory, value);
        });

        pCategoryRepository.save(existingCategory);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateCategory");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> deleteCategory(String id) {

        try {
            pCategoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new SomethingWentWrong("Deleting Failed!!!!!!");
        }
        SuccessBody body = new SuccessBody("تم حذف البيانات بنجاح", "/api/dashboard/deleteCategory");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

}
