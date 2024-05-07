package com.shatbha_shop.shatbha_shop.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shatbha_shop.shatbha_shop.Validations.CityValidation.CityExist;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
@Document(collection = "users")
public class User implements UserDetails{

    @Id
    private String id;

    @NotBlank(message = "الرجاء ادخال الاسم الاول")
    @NotNull(message = "الرجاء ادخال الاسم الاول")
    private String firstname;

    @NotBlank(message = "الرجاء ادخال الاسم الاخير")
    @NotNull(message = "الرجاء ادخال الاسم الاخير")
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

    @DocumentReference(collection = "cities")
    @CityExist
    @NotNull(message = "الرجاء اختيار المدينة")
    private City city;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }

}