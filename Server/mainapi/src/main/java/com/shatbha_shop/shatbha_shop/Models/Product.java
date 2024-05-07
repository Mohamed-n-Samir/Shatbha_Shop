package com.shatbha_shop.shatbha_shop.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotBlank
    private String description;

    @NotNull
    private double oldPrice;

    private Double newPrice;

    @DocumentReference(collection = "pcategories")
    @NotNull
    private PCategory category;

    @DocumentReference(collection = "brands")
    private Brand brand;

    @NotNull
    private int quantity;

    private int minQuantity = 1;

    private int sold = 0;

    private List<Image> images;

    private List<String> color;

    private List<String> tags;

    // private List<Rating> ratings;

    private String totalRating = "0";

    // private AdditionalInfo additionalInfo;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        @Id
        private String id;
        private int imageId;
        private String url;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rating {
        private int star;
        private String comment;
        @DBRef
        private User postedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalInfo {
        private String weight;
        private String dimensions;
        private String special;
        private String infoType;
        private String colors;
    }

    public Product(String id){
        this.id = id;
    }
}
