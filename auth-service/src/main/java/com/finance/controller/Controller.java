package com.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping ("/api")    
public class Controller {
    
    @GetMapping("/home")
    public String home() {
        return "Welcome to the Finance Application!";
    }   
    
    @GetMapping("/user")
    public String userProfile() {
        return "USER ACCESS OK";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "ADMIN ACCESS OK";
    	
    }
}
