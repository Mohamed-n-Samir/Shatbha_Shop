package com.shatbha_shop.shatbha_shop.Validations.CityValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityValidator.class)
public @interface CityExist {
    String message() default "المدينه غير موجودة";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}