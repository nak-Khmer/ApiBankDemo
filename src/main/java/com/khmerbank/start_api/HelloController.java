package com.khmerbank.start_api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController 
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello controler";
    }
    @GetMapping ("/sum")
    public int sum(int a, int b){
        return a + b;
    }
}