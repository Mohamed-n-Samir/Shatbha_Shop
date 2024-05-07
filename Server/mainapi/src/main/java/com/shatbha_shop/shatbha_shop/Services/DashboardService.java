package com.shatbha_shop.shatbha_shop.Services;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shatbha_shop.shatbha_shop.Exceptions.SomethingWentWrong;
import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

@Service
public class DashboardService {

    private final WebClient webClient = WebClient.builder().build();

    public List<Order> getallorders() {

        ParameterizedTypeReference<List<Order>> typeReference = new ParameterizedTypeReference<List<Order>>() {
        };

        return webClient.get().uri("http://orderserviceContainer:8081/api/getallorders").retrieve()
                .bodyToMono(typeReference).block();
    }

    public ResponseEntity<SuccessBody> updateOrderByFields(String Id, Map<String, Object> fields) {

        boolean res = webClient.patch().uri("http://orderserviceContainer:8081/api/updateOrder/" + Id).bodyValue(fields).retrieve()
                .bodyToMono(boolean.class).block();

        if (res) {
            SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateOrder");
            return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
        } else {
            throw new SomethingWentWrong("Update Faild");
        }
    }
    

}
