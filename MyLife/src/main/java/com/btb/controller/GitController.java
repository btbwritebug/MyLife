package com.btb.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gitHub")
@Api(value = "Git推送接口", tags = {"Git推送接口"})
public class GitController {
    @GetMapping("/hello")
    public String test() {
        return "hello world";
    }
}
