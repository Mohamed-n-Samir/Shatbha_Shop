package com.shatbha_shop.shatbha_shop.Validations.CityValidation;

import org.springframework.beans.factory.annotation.Autowired;

import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Repositories.CityRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CityValidator implements ConstraintValidator<CityExist, City> {

    @Autowired
    private CityRepository cityRepository;
    // private CityServices cityServices;

    @Override
    public void initialize(CityExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(City city, ConstraintValidatorContext context) {

        // return !cityServices.GetCityByID(city.getId()).equals(null);
        System.out.println(cityRepository.findById(city.getId()).isEmpty());
        return !cityRepository.findById(city.getId()).isEmpty();
    }
}
