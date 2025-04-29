package com.hanasign.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/users/contract")
    public String contractPage() {
        return "forward:/index.html";
    }
} 