package com.shatbha_shop.shatbha_shop.Models;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PaymentIntent {

    private String id;
    private String method;
    private double amount;
    private String currency;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;


}

