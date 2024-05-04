package com.shatbha_shop.shatbha_shop.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Repositories.CityRepository;

@Service
public class CityService {
    
    @Autowired
    private CityRepository cityRepository;

    List<City> result = new ArrayList<City>();
 
    public List<City> GetAllCitys(){
        return cityRepository.findAll();
    }

    public City GetCityByID (String id){
        return cityRepository.findById(id).orElseThrow(() -> new NotFoundException("المدينه غير موجودة"));
    }

    @SuppressWarnings({"unused" })
    public String AddCity(City city){
        if(cityRepository.findByName(city.getName()) != null){
            throw new ConflictException("بيانات المدينه موجوده بالفعل");
        }
        cityRepository.insert(city); 
        return "City Added Successfully";
    }

    public void DeleteCity(String id){
        cityRepository.deleteById(id);
    }
}

