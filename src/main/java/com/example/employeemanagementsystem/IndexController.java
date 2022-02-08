package com.example.employeemanagementsystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//for controlling the index.html
@Controller
public class IndexController {
    @GetMapping(path="/")
    public String home() {
        return "index";
    }
}
