package com.orderservice.orderservice.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.orderservice.orderservice.Model.City;
import com.orderservice.orderservice.Model.User;
import com.orderservice.orderservice.ModelsHelper.Product;

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
