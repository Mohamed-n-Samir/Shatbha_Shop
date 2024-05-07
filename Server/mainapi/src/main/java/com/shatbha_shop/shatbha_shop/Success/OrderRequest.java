package com.shatbha_shop.shatbha_shop.Success;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.ModelsHelper.Product;

@Data
@NoArgsConstructor
public class OrderRequest {
    private List<Product> products;
    private City city;
    private String area;
    private String buildingAndApartment;
    private String notes;
    private User user;


}