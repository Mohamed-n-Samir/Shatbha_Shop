package com.orderservice.orderservice.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.orderservice.orderservice.Exceptions.NotFoundException;
import com.orderservice.orderservice.Model.City;
import com.orderservice.orderservice.Model.Order;
import com.orderservice.orderservice.Model.PaymentIntent;
import com.orderservice.orderservice.Model.User;
import com.orderservice.orderservice.ModelsHelper.Product;
import com.orderservice.orderservice.Repositories.OrderRepository;
import com.orderservice.orderservice.Repositories.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllUserOrders(String id) {

        return orderRepository.findAllByOrderby(new User(id));
    }

    public boolean CreateOrder(User user, List<Product> products, City city,
            String area, String buildingAndApartment, String notes) {

        double cartTotal = 0;
        String address = "";
        double shippingPrice = 0;

        if (products == null || products.isEmpty()) {
            throw new NotFoundException("لا يوجد منتجات في السله");
        }

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
            return false;
        }

        return true;
    }

    public Boolean updateOrderByFields(String Id, Map<String, Object> fields) {

        Order existingOrder = orderRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Order.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingOrder, value);
        });

        try {
            orderRepository.save(existingOrder);
        } catch (Exception e) {
            return false;
        }
        
        return true;


    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

}
