package com.shatbha_shop.shatbha_shop.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank(message = "الرجاء ادخال الاسم الاول")
    private String firstname;

    @NotBlank(message = "الرجاء ادخال الاسم الاخير")
    private String lastname;

    @Email(message = "الرجاء ادخال بريد الكتروني صحيح")
    @NotBlank(message = "الرجاء ادخال البريد الالكتروني")
    private String email;

    @Pattern(regexp="(^$|[0-9]{11})", message = "الرجاء ادخال رقم هاتف صحيح")
    @NotBlank(message = "الرجاء ادخال رقم الهاتف")
    private String mobile;

    @NotBlank(message = "الرجاء ادخال كلمة المرور")
    private String password;

    private String role = "user";

    private boolean isBlocked = false;

    private List<String> cart = new ArrayList<>();

    @NotNull(message = "الرجاء اختيار المدينة")
    private ObjectId city;

    @NotBlank(message = "الرجاء اختيار المنطقة")
    private String area;

    @NotBlank(message = "الرجاء ادخال رقم المبنى")
    private String buildingAndApartment;

    @NotBlank(message = "الرجاء اختيار الجنس")
    private String gender;

    private List<String> wishlist = new ArrayList<>();

    private String refreshToken = "";

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public ObjectId getCityId() {
        return city;
    }

    public void setCityId(String cityId) {
        this.city = new ObjectId(cityId);
    }

    public void setCityId(ObjectId cityId) {
        this.city = cityId;
    }
}

// {
//     "firstname":"ahmed",
//     "lastname": "mohamed",
//     "email":"ahmod@gmail.com",
//     "mobile": "01111145255",
//     "password": "test1234",
//     "role" : "user",
//     "isBlocked": false,
//     "city": "64d2a3cb63e6091d09469663",
//     "area": "asdf",
//     "buildingAndApartment": "asdfafsd",
//     "gender": "Male"
//   }