package com.orderservice.orderservice.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstname;
    private String lastname;
    private String mobile;

    @DocumentReference(collection = "cities")
    @NotNull(message = "الرجاء اختيار المدينة")
    private City city;

    @NotBlank(message = "الرجاء اختيار المنطقة")
    private String area;

    @NotBlank(message = "الرجاء ادخال رقم المبنى")
    private String buildingAndApartment;

    public User(String id){
        this.id = id;
    }
}
