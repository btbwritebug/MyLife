package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/hello", method = RequestMethod.GET)
public class RequestMappingController {

    @RequestMapping("/testRequestMapping")
    public String success() {
        return "success";
    }


}
