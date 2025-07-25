package com.hoangminh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangminh.service.BookingService;
import com.hoangminh.service.TourService;
import com.hoangminh.service.UserService;
import com.hoangminh.utilities.SessionUtilities;
import jakarta.servlet.annotation.HandlesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    TourService tourService;
    @Autowired
    BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/index")
    public String dashboard(Model model) throws JsonProcessingException {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        model.addAttribute("userCount", userService.countAllUsers());
        model.addAttribute("tourCount", tourService.countAllTours());
        model.addAttribute("totalRevenue", bookingService.getTotalRevenue());
        model.addAttribute("averageRating", tourService.getAverageRating());
        // Serialize dữ liệu biểu đồ sang JSON
        model.addAttribute("tourMonthStatsJson", objectMapper.writeValueAsString(tourService.countToursByMonth()));
        model.addAttribute("tourSeasonStatsJson", objectMapper.writeValueAsString(tourService.countToursBySeason()));
        return "admin/index";
    }

    @GetMapping("/user")
    public String userManage() {

        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }

        return "admin/user";
    }

    @GetMapping("/tour")
    public String tourManage() {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        return "admin/tour";
    }

    @GetMapping("/booking")
    public String bookingManager() {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        return "admin/booking";
    }

    @GetMapping("/review")
    public String reviewManage() {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        return "admin/review";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/tourStart/{id}")
    public String tourStart(@PathVariable("id")Long id) {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        return "admin/tourstart";
    }
    @GetMapping("/tourImage/{id}")
    public String tourImage(@PathVariable("id") Long id) {
        if(!this.userService.checkAdminLogin()) {
            return "redirect:/admin/login";
        }
        return "admin/tourImage";
    }

    @GetMapping("/upload-test")
    public String uploadTest() {
        return "admin/upload-test";
    }

    @GetMapping("/logout")
    public String adminLogout() {
        this.userService.adminLogout();
        return "redirect:/admin/login";
    }
}
