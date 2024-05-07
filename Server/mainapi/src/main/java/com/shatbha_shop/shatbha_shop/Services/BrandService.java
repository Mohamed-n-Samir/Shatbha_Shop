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
import com.shatbha_shop.shatbha_shop.Models.Brand;
import com.shatbha_shop.shatbha_shop.Repositories.BrandRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> GetAllBrands() {
        return brandRepository.findAll();
    }

    public Brand GetBrandByID(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("الماركه غير موجوده"));
    }

    public String AddBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()) != null) {
            throw new ConflictException("بيانات الماركه موجوده بالفعل");
        }
        brandRepository.insert(brand);
        return "Brand Added Successfully";
    }

    public ResponseEntity<SuccessBody> deleteBrand(String id) {
        try {
            brandRepository.deleteById(id);
        } catch (Exception e) {
            throw new SomethingWentWrong("Deleting Failed!!!!!!");
        }
        SuccessBody body = new SuccessBody("تم حذف البيانات بنجاح", "/api/dashboard/deleteBrand");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> addBrand(Brand brand){

        brandRepository.save(brand);

        SuccessBody body = new SuccessBody("تم اضافه الماركه بنجاح", "/api/dashboard/createBrand");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);

    }

    public ResponseEntity<SuccessBody> updateBrandByFields(String Id, Map<String, Object> fields) {

        Brand existingBrand = brandRepository.findById(Id).orElseThrow(() -> new NotFoundException("Brand not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Brand.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingBrand, value);
        });

        brandRepository.save(existingBrand);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateBrand");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }
}
