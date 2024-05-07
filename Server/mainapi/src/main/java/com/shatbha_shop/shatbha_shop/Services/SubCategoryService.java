package com.shatbha_shop.shatbha_shop.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Models.SubCategory;
import com.shatbha_shop.shatbha_shop.Repositories.PCategoryRepository;
import com.shatbha_shop.shatbha_shop.Repositories.SubCategoryReposirory;
import com.shatbha_shop.shatbha_shop.Success.SubCat;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryReposirory subCategoryReposirory;

    @Autowired
    private PCategoryRepository pCategoryRepository;

    public List<SubCategory> getAllSubCategoriesData() {
        return subCategoryReposirory.findAll();
    }

    public List<SubCat> getAllSubCategories() {
        List<SubCat> subCats = new ArrayList<SubCat>();
        List<SubCategory> allSubCategories = subCategoryReposirory.findAll();
        if (!allSubCategories.isEmpty()) {
            for (SubCategory subCategory : allSubCategories) {
                for (String tag : subCategory.getSubCategory()) {
                    subCats.add(new SubCat(tag, tag));
                }
            }
        }
        return subCats;
    }

    public SubCategory getCreateSubCategory(String id) {
        PCategory category = pCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("القسم غير موجود"));

        List<SubCategory> subCategory = subCategoryReposirory.findAllByCategory(category);

        if (subCategory.size() > 0) {
            return subCategory.get(0);
        } else {
            List<String> tags = new ArrayList<String>();
            ;
            return subCategoryReposirory.save(new SubCategory(null, category, tags));
        }

    }

    public ResponseEntity<SuccessBody> updateSubCategoryByFields(String Id, Map<String, Object> fields) {

        SubCategory existingSubCategory = subCategoryReposirory.findById(Id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(SubCategory.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingSubCategory, value);
        });

        subCategoryReposirory.save(existingSubCategory);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateSubCategory");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

}
