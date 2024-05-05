package com.shatbha_shop.shatbha_shop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.ModelsHelper.Product;
import com.shatbha_shop.shatbha_shop.Services.OrderService;
import com.shatbha_shop.shatbha_shop.Success.OrderRequest;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/getOrders")
    public List<Order> getOrders(HttpServletRequest request) {
        return orderService.getAllUserOrders(request);
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
        
        return orderService.CreateOrder(request, orderRequest.getProducts(), orderRequest.getCity(),
                orderRequest.getArea(),
                orderRequest.getBuildingAndApartment(), orderRequest.getNotes());
        
        // throw new NotFoundException("aa");
    }

    // @PostMapping("/api/createOrder")
    // public ResponseEntity<SuccessBody> createOrder(HttpServletRequest request,
    // @RequestParam City city,
    // @RequestParam String area, @RequestParam String buildingAndApartment,
    // @RequestParam String notes) {
    // System.out.println("Received order request:");
    // // System.out.println("Products: " + products);
    // System.out.println("City: " + city);
    // System.out.println("Area: " + area);
    // System.out.println("Building and Apartment: " +
    // buildingAndApartment);
    // System.out.println("Notes: " + notes);

    // throw new NotFoundException("aa");
    // // return orderService.CreateOrder(request, products, city, area,
    // // buildingAndApartment, notes);
    // }

}
