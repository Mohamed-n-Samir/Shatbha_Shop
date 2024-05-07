package com.orderservice.orderservice.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderservice.orderservice.Model.Order;
import com.orderservice.orderservice.Model.User;

public interface OrderRepository extends MongoRepository<Order,String>{

    List<Order> findAllByOrderby(User orderby);
}
