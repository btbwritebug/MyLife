package com.atguigu.admin.controller;

import com.atguigu.admin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession session, Model model) {
        if (!StringUtils.isEmpty(user.getUserName()) && "123456".equals(user.getPassword())) {
            //把登录成功的用户保存起来
            session.setAttribute("loginUser", user);
            //成功跳转
            return "redirect:/main.html";
        } else {
            model.addAttribute("msg", "账号密码错误");
            return "login";
        }

    }

    /**
     * 重定向
     *
     * @return
     */
    @GetMapping("/main.html")
    public String mainPage(HttpSession session,Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null){
            return "main";
        }
        else {
            //回到登录页
            model.addAttribute("msg","请重新登录");
            return "login";
        }
    }
}
