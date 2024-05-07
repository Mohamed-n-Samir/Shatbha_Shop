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

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.SomethingWentWrong;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Repositories.CityRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    List<City> result = new ArrayList<City>();

    public List<City> GetAllCitys() {
        return cityRepository.findAll();
    }

    public City GetCityByID(String id) {
        return cityRepository.findById(id).orElseThrow(() -> new NotFoundException("المدينه غير موجودة"));
    }

    public String AddCity(City city) {
        if (cityRepository.findByName(city.getName()) != null) {
            throw new ConflictException("بيانات المدينه موجوده بالفعل");
        }
        cityRepository.insert(city);
        return "City Added Successfully";
    }

    public void DeleteCity(String id) {
        cityRepository.deleteById(id);
    }

    public ResponseEntity<SuccessBody> updateCityByFields(String Id, Map<String, Object> fields) {

        City existingCity = cityRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("City not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(City.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingCity, value);
        });

        cityRepository.save(existingCity);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateCity");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> deleteCity(String id) {
        try {
            cityRepository.deleteById(id);
        } catch (Exception e) {
            throw new SomethingWentWrong("Deleting Failed!!!!!!");
        }
        SuccessBody body = new SuccessBody("تم حذف البيانات بنجاح", "/api/dashboard/deleteCity");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

}
