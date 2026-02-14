package com.alok.bankingapp.controller;

import com.alok.bankingapp.dto.HelloResponse;
import com.alok.bankingapp.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController (HelloService helloService){
        this.helloService=helloService;
    }
    @GetMapping("/hello")
    public HelloResponse hello(){
        return helloService.generateHello();
    }
}
