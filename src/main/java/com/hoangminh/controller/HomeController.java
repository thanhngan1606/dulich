package com.hoangminh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping("/")
	ModelAndView index() {
		ModelAndView mdv = new ModelAndView("user/index");
		
		return mdv;
	}
	
}
