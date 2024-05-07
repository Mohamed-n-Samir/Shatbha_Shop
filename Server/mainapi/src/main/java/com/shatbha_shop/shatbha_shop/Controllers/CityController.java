package com.shatbha_shop.shatbha_shop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Services.CityService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/api/getAllCities")
    public List<City> GetAllCitys() {
        return cityService.GetAllCitys();
    }

    @PostMapping("/api/addCity")
    public String CityRegister(@RequestBody @Valid City city) {
        return cityService.AddCity(city);
    }
    
}
