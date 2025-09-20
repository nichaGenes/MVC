package com.courseregistration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "redirect:/course-registration.html";
    }
    
    @GetMapping("/home")
    public String home() {
        return "redirect:/course-registration.html";
    }
}
