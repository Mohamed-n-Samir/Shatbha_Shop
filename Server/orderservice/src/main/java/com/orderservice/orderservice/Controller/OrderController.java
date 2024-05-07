package com.orderservice.orderservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.orderservice.Model.Order;
import com.orderservice.orderservice.Service.OrderService;
import com.orderservice.orderservice.Requests.OrderRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("getOrders/{id}")
    public List<Order> getOrders(@PathVariable String id) {
        return orderService.getAllUserOrders(id);
    }

    @GetMapping("getallorders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    

    @PostMapping("createOrder")
    public boolean createOrder(@RequestBody OrderRequest orderRequest) {

        System.out.println("Received order request:");
        System.out.println("Products: " + orderRequest.getProducts());
        System.out.println("City: " + orderRequest.getCity());
        System.out.println("Area: " + orderRequest.getArea());
        System.out.println("Building and Apartment: " +
                orderRequest.getBuildingAndApartment());
        System.out.println("Notes: " + orderRequest.getNotes());

        return orderService.CreateOrder(orderRequest.getUser(), orderRequest.getProducts(), orderRequest.getCity(),
                orderRequest.getArea(),
                orderRequest.getBuildingAndApartment(), orderRequest.getNotes());
    }

    @PatchMapping("updateOrder/{id}")
    public boolean updateOrderByFields(@PathVariable String id,
            @RequestBody Map<String, Object> data) {
        return orderService.updateOrderByFields(id, data);
    }
}
