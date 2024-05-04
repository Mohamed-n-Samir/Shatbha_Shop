package com.shatbha_shop.shatbha_shop.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cities")
public class City {

    @Id
    private String id;
    private String name;
    private int shippingCharge;

    public City(String id){
        this.id = id;
    }

}
