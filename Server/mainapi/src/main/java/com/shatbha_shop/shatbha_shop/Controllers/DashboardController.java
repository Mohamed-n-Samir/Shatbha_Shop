package com.shatbha_shop.shatbha_shop.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.shatbha_shop.shatbha_shop.Models.Brand;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Models.PCategory;
import com.shatbha_shop.shatbha_shop.Models.Product;
import com.shatbha_shop.shatbha_shop.Models.SubCategory;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Services.BrandService;
import com.shatbha_shop.shatbha_shop.Services.CityService;
import com.shatbha_shop.shatbha_shop.Services.DashboardService;
import com.shatbha_shop.shatbha_shop.Services.PCategoryService;
import com.shatbha_shop.shatbha_shop.Services.ProductService;
import com.shatbha_shop.shatbha_shop.Services.SubCategoryService;
import com.shatbha_shop.shatbha_shop.Services.UserService;
import com.shatbha_shop.shatbha_shop.Success.AllIncomes;
import com.shatbha_shop.shatbha_shop.Success.ChartData;
import com.shatbha_shop.shatbha_shop.Success.DailyIncome;
import com.shatbha_shop.shatbha_shop.Success.SubCat;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/api/dashboard/")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PCategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("chart")
    public List<ChartData> getChartData() {
        List<ChartData> chartDatas = new ArrayList<ChartData>();
        List<DailyIncome> dailyIncomes = new ArrayList<DailyIncome>();
        DailyIncome dailyIncome = new DailyIncome(23, 100);
        dailyIncomes.add(dailyIncome);
        ChartData chartData = new ChartData("July", dailyIncomes, 2023, 7);
        chartDatas.add(chartData);
        return chartDatas;
    }

    @GetMapping("featured")
    public AllIncomes allIncomes() {
        AllIncomes allIncomes = new AllIncomes(0.5, 10000.0, 10000, 100000, 100000);
        return allIncomes;
    }

    @GetMapping("allUsers")
    public List<User> getAllUsers() {
        return userService.GetAllUsers();
    }

    @GetMapping("allAdmins")
    public List<User> getAllAdmins() {
        return userService.GetAllAdmins();
    }

    @GetMapping("getAllCities")  
    public List<City> getAllCities() {
        return cityService.GetAllCitys();
    }

    @GetMapping("allProduct")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("allCategory")
    public List<PCategory> getAllCategory() {
        return categoryService.GetAllPCategorys();
    }

    @GetMapping("getAllSubCategory")
    public List<SubCat> getAllSubCategory() {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("getAllSubCategoryData")
    public List<SubCategory> getAllSubCategoryData() {
        return subCategoryService.getAllSubCategoriesData();
    }

    @GetMapping("getCreateSubCategory/{id}")
    public SubCategory getCreateSubCategory(@PathVariable String id) {
        return subCategoryService.getCreateSubCategory(id);
    }

    @GetMapping("allBrands")
    public List<Brand> getAllBrands() {
        return brandService.GetAllBrands();
    }

    @GetMapping("getallorders")
    public List<Order> getallorders() {
        return dashboardService.getallorders();
    }
    
    @PostMapping("createAdmin")
    public ResponseEntity<SuccessBody> addAdmin(@RequestBody @Valid User user) {        
        return userService.AddAdmin(user);
    }

    @PostMapping("createProduct")
    public ResponseEntity<SuccessBody> addProduct(@RequestBody @Valid Product product) {        
        return productService.addProduct(product);
    }

    @PostMapping("createBrand")
    public ResponseEntity<SuccessBody> addBrand(@RequestBody @Valid Brand brand) {        
        return brandService.addBrand(brand);
    }
    
    @PatchMapping("updateUser-admin/{id}")
    public ResponseEntity<SuccessBody> updateUser(@PathVariable String id,@RequestBody Map<String,Object> data){
        return userService.updateUserByFields(id, data);
    }

    @PatchMapping("updateProduct/{id}")
    public ResponseEntity<SuccessBody> updateProductByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return productService.updateProductByFields(id, data);
    }

    @PatchMapping("updateBrand/{id}")
    public ResponseEntity<SuccessBody> updateBrandByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return brandService.updateBrandByFields(id, data);
    }

    @PatchMapping("updateCategory/{id}")
    public ResponseEntity<SuccessBody> updateCategoryByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return categoryService.updateCategoryByFields(id, data);
    }

    @PatchMapping("updateSubCategory/{id}")
    public ResponseEntity<SuccessBody> updateSubCategoryByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return subCategoryService.updateSubCategoryByFields(id, data);
    }

    @PatchMapping("updateOrder/{id}")
    public ResponseEntity<SuccessBody> updateOrderByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return dashboardService.updateOrderByFields(id, data);
    }

    @PatchMapping("updateCity/{id}")
    public ResponseEntity<SuccessBody> updateCityByFields(@PathVariable String id,@RequestBody Map<String,Object> data){
        return cityService.updateCityByFields(id, data);
    }

    @DeleteMapping("deleteBrand")
    public ResponseEntity<SuccessBody> deleteBrand(@RequestBody String data){
        return brandService.deleteBrand(data);
    }

    @DeleteMapping("deleteProduct")
    public ResponseEntity<SuccessBody> deleteProduct(@RequestBody String data){
        return productService.deleteProduct(data);
    }

    @DeleteMapping("deleteCategory")
    public ResponseEntity<SuccessBody> deleteCategory(@RequestBody String data){
        return categoryService.deleteCategory(data);
    }

    @DeleteMapping("deleteCity")
    public ResponseEntity<SuccessBody> deleteCity(@RequestBody String data){
        return cityService.deleteCity(data);
    }
    
    

}
