package com.example.retakeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmptyController {
    @GetMapping()
    public String get(){
        return "auth/login";
    }
}
