package com.shatbha_shop.shatbha_shop.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.Product;
import com.shatbha_shop.shatbha_shop.Services.ProductService;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/api/allProducts")
    public List<Product> getAllProductsTest() {
        return productService.findAll();
    }

    @GetMapping("/api/getOffers")
    public List<Product> getOffers() {
        return productService.getOffers();
    }
    

    @GetMapping("/api/allProductForUsers")
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer limit,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer newPricegte,
            @RequestParam(required = false) Integer newPricelte,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String tags

    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Product> productsPage = productService.search(title,page, limit, newPricegte,newPricelte,sort,tags,pageable);
        return productsPage;

    }
}
