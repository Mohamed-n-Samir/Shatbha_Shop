package com.shatbha_shop.shatbha_shop.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("")
public class HomeController {


    @GetMapping("")
    public String greeting(){
        return "hello! world";
    }
}
