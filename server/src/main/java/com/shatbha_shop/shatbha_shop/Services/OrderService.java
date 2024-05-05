package com.shatbha_shop.shatbha_shop.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shatbha_shop.shatbha_shop.Exceptions.BaseException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.WrongTokenException;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Models.PaymentIntent;
import com.shatbha_shop.shatbha_shop.ModelsHelper.Product;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Repositories.OrderRepository;
import com.shatbha_shop.shatbha_shop.Repositories.ProductRepository;
import com.shatbha_shop.shatbha_shop.Repositories.UserRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<SuccessBody> saveOrder(Order order) {

        try {
            orderRepository.save(order);

        } catch (Exception e) {
            throw new WrongTokenException("Something Wrong With Your Order");
        }

        SuccessBody body = new SuccessBody("Order Created Successfully", "/api/createOrder");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);

    }

    public List<Order> getAllUserOrders(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WrongTokenException("Token not found or wrong");
        }

        System.out.println(authHeader);

        String email = jwtService.extractUsername(authHeader.substring(7));

        User orderby = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        return orderRepository.findAllByOrderby(orderby);
    }











    public ResponseEntity<SuccessBody> CreateOrder(HttpServletRequest request, List<Product> products, City city,
            String area, String buildingAndApartment, String notes) {

        double cartTotal = 0;
        String address = "";
        double shippingPrice = 0;

        if (products == null || products.isEmpty()) {
            throw new NotFoundException("لا يوجد منتجات في السله");
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WrongTokenException("Token not found or wrong");
        }

        System.out.println(authHeader);

        String email = jwtService.extractUsername(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        if (city.getId().isEmpty() || area.isEmpty() || area == null || buildingAndApartment == null
                || buildingAndApartment.isEmpty()) {
            city = user.getCity();
            area = user.getArea();
            buildingAndApartment = user.getBuildingAndApartment();
        }
        address = city.getName() + "/" + area + "/" + buildingAndApartment;
        shippingPrice = city.getShippingCharge();

        
        for (Product product : products) {
            System.out.println(product.getId());
            double price = productRepository.findById(product.getProduct().getId()).get().getNewPrice();
            cartTotal += price * product.getQuantity();
        }
        
        // System.out.println(user);
        Order order = new Order();
        order.setProducts(products);
        PaymentIntent paymentIntent = new PaymentIntent();
        paymentIntent.setId(UUID.randomUUID().toString());
        paymentIntent.setMethod("COD");
        paymentIntent.setAmount(cartTotal + shippingPrice);
        // paymentIntent.setCreated(System.currentTimeMillis());
        paymentIntent.setCurrency("EGP");
        order.setPaymentIntent(paymentIntent);
        order.setOrderby(user);
        order.setOrderStatus("Not Processed");
        order.setDestinationAddress(address);
        order.setNotes(notes);

        try {
            orderRepository.insert(order);

        } catch (Exception e) {
            throw new WrongTokenException("Something Wrong With Your Order");
        }

        SuccessBody body = new SuccessBody("Order Created Successfully", "/api/createOrder");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

}
