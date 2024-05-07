package com.shatbha_shop.shatbha_shop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Services.UserService;
import com.shatbha_shop.shatbha_shop.Success.OrderRequest;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/getOrders")
    public List<Order> getOrders(HttpServletRequest request) {
        return userService.getAllUserOrders(request);
    }

    @PostMapping("/api/createOrder")
    public ResponseEntity<SuccessBody> createOrder(HttpServletRequest request, @RequestBody OrderRequest orderRequest) {

        System.out.println("Received order request:");
        System.out.println("Products: " + orderRequest.getProducts());
        System.out.println("City: " + orderRequest.getCity());
        System.out.println("Area: " + orderRequest.getArea());
        System.out.println("Building and Apartment: " +
                orderRequest.getBuildingAndApartment());
        System.out.println("Notes: " + orderRequest.getNotes());

        return userService.CreateOrder(request, orderRequest);

    }

}
