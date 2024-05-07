package com.orderservice.orderservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String title;

    private Double newPrice;

    private List<Image> images;

    public Product(String id){
        this.id = id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        @Id
        private String id;
        private int imageId;
        private String url;
    }
}
