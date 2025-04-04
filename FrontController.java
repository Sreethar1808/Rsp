package com.example.DemoProject.RSProject.practice;

//package com.example.DemoProject.RSProject.practice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @GetMapping("/geturl")
    public String home() {
    	System.out.println("sytem get");
        return "index";  
    }
}
