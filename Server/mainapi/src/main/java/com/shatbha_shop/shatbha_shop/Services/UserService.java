package com.shatbha_shop.shatbha_shop.Services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.shatbha_shop.shatbha_shop.Exceptions.ConflictException;
import com.shatbha_shop.shatbha_shop.Exceptions.NotFoundException;
import com.shatbha_shop.shatbha_shop.Exceptions.SomethingWentWrong;
import com.shatbha_shop.shatbha_shop.Models.City;
import com.shatbha_shop.shatbha_shop.Models.Order;
import com.shatbha_shop.shatbha_shop.Models.User;
import com.shatbha_shop.shatbha_shop.Repositories.UserRepository;
import com.shatbha_shop.shatbha_shop.Success.OrderRequest;
import com.shatbha_shop.shatbha_shop.Success.SuccessBody;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final WebClient webClient = WebClient.builder().build();

    List<User> result = new ArrayList<User>();

    public List<User> GetAllUsers() {
        return userRepository.findAllByRole("user");
    }

    public List<User> GetAllAdmins() {
        return userRepository.findAllByRole("admin");
    }

    public User GetUserByID(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("المستخدم غير موجود"));
    }

    public ResponseEntity<SuccessBody> AddUser(User user) {
        if (userRepository.findByEmailOrMobile(user.getEmail(), user.getMobile()) != null) {
            throw new ConflictException("بيانات المستخدم موجوده بالفعل");
        }
        userRepository.insert(user);
        SuccessBody body = new SuccessBody("Register Success ! login now", "/api/register");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> AddAdmin(User user) {
        if (userRepository.findByEmailOrMobile(user.getEmail(), user.getMobile()) != null) {
            throw new ConflictException("بيانات المستخدم موجوده بالفعل");
        }

        user.setRole("admin");

        userRepository.insert(user);
        SuccessBody body = new SuccessBody("Admin Registered Successfully", "/api/dashboard/createAdmin");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public void DeleteUser(String id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<SuccessBody> updateUserByFields(String Id, Map<String, Object> fields) {

        User existingUser = userRepository.findById(Id).orElseThrow(() -> new NotFoundException("user not found"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            field.setAccessible(true);
            if (key == "city") {
                ReflectionUtils.setField(field, existingUser, new City(value.toString()));
            } else {
                ReflectionUtils.setField(field, existingUser, value);

            }
        });

        userRepository.save(existingUser);

        SuccessBody body = new SuccessBody("تم تحديث البيانات بنجاح", "/api/dashboard/updateUser-admin");
        return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<SuccessBody> CreateOrder(HttpServletRequest request, @RequestBody OrderRequest orderRequest) {

        if (orderRequest.getProducts() == null || orderRequest.getProducts().isEmpty()) {
            throw new NotFoundException("لا يوجد منتجات في السله");
        }

        String email = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        orderRequest.setUser(user);

        boolean res = webClient.post().uri("http://orderserviceContainer:8081/api/createOrder").bodyValue(orderRequest).retrieve()
                .bodyToMono(boolean.class).block();

        if (res == true) {
            SuccessBody body = new SuccessBody("Order Created Successfully", "/api/createOrder");
            return new ResponseEntity<SuccessBody>(body, HttpStatus.ACCEPTED);
        } else {
            throw new SomethingWentWrong("Something Wrong With Your Order");
        }

    }

    public List<Order> getAllUserOrders(HttpServletRequest request) {

        String email = jwtService.extractUsername(request.getHeader("Authorization").substring(7));

        User orderby = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        ParameterizedTypeReference<List<Order>> typeReference = new ParameterizedTypeReference<List<Order>>() {
        };

        return webClient.get().uri("http://orderserviceContainer:8081/api/getOrders/" + orderby.getId()).retrieve().bodyToMono(typeReference).block();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
