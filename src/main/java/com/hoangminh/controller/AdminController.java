package com.hoangminh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping("/user")
    public String userManage() {
        return "admin/user";
    }

    @GetMapping("/tour")
    public String tourManage() {
        return "admin/tour";
    }

    @GetMapping("/booking")
    public String bookingManager() {
        return "admin/booking";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

}
