package com.example.controller;

import com.example.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String index() {
        return "sad";
    }

    @RequestMapping("/student111")
    public Student student() {
        Student student = new Student();
        student.setName("鲍廷博");
        student.setSex("男");
        student.setSid(24);
        return student;
    }


}
