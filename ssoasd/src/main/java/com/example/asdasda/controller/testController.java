package com.example.asdasda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

    @RequestMapping("/index")
    public String get(){
        return "static/index.html";
    }
}
