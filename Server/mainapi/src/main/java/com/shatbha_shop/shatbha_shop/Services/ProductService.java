package com.shatbha_shop.shatbha_shop.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.bson.types.ObjectId;

import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.SomethingWentWrong;
import com.shatbha_shop.shatbha_shop.Models.Product;
import com.shatbha_shop.shatbha_shop.Repositories.ProductRepository;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;



@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    List<Product> result = new ArrayList<Product>();

    public Product GetProductByID(String id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("المدينه غير موجودة"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> getOffers() {
        return productRepository.findByNewPriceGreaterThan(0).stream().limit(4).collect(Collectors.toList());
    }

    public Product getaProduct(String slug) {
        return productRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("هذا المنتج غير موجود"));
    }

    public void DeleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<SuccessBody> addProduct(Product product) {

        productRepository.save(product);

        SuccessBody body = new SuccessBody("تم حفظ بنجاح", "/api/dashboard/createProduct");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> updateProductByFields(String Id, Map<String, Object> fields) {

        Product existingProduct = productRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            field.setAccessible(true);
            if (key == "newPrice" | key == "oldPrice" | key == "quantity") {
                ReflectionUtils.setField(field, existingProduct, Integer.parseInt(value.toString()));
            } else {
                ReflectionUtils.setField(field, existingProduct, value);
            }

        });

        productRepository.save(existingProduct);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateProduct");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> deleteProduct(String id) {

        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new SomethingWentWrong("Deleting Failed!!!!!!");
        }
        SuccessBody body = new SuccessBody("تم حذف البيانات بنجاح", "/api/dashboard/deleteProduct");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public Page<Product> search(String title, Integer page, Integer limit, Integer newPricegte, Integer newPricelte,
            String sort,
            String tag, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<Criteria>();

        if (title != null && !title.isEmpty()) {
            System.out.println(
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa5555555555555555555555555555555555555555555555555555555555555"
                            + title);
            criteria.add(Criteria.where("title").regex(title, "i"));
        }
        if (newPricegte != null && newPricelte != null) {
            criteria.add(Criteria.where("newPrice").gte(newPricegte).lte(newPricelte));
        }

        if (tag != null && !tag.isEmpty()) {
            if (ObjectId.isValid(tag)) {
                criteria.add(Criteria.where("category.id").is(tag));
            } else {
                criteria.add(Criteria.where("tags").in(tag));

            }
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            if (sort == "-createdAt") {
                query.with(Sort.by(Sort.Direction.ASC, "_id"));
            } else {
                query.with(Sort.by(Sort.Direction.DESC, "_id"));

            }
        }

        Page<Product> products = PageableExecutionUtils.getPage(mongoTemplate.find(query, Product.class), pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Product.class));

        return products;
    }
}
