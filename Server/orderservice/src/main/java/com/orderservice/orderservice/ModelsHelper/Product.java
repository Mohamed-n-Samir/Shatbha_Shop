package com.orderservice.orderservice.ModelsHelper;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @DocumentReference(collection = "products")
    private com.orderservice.orderservice.Model.Product product;

    private int quantity;

    public Product(String product, int quantity){
        this.product.setId(product);
        this.quantity = quantity;
    }


}
