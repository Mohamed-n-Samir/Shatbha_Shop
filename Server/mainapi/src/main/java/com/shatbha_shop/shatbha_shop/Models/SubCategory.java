package com.shatbha_shop.shatbha_shop.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subcategories")
public class SubCategory {

    @Id
    private String id;

    @DocumentReference(collection = "pcategories")
    @NotNull
    private PCategory category;

    private List<String> subCategory;

}
