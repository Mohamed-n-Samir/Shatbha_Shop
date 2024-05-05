package com.shatbha_shop.shatbha_shop.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Models.User;

public interface OrderRepository extends MongoRepository<Order,String>{

    List<Order> findAllByOrderby(User orderby);
}
