package com.hoangminh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hoangminh.entity.User;
import com.hoangminh.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class AccountController {
	
	@Autowired 
	private UserServiceImpl userService;
	
	@GetMapping("/")
	ModelAndView getAllUser(@RequestParam int page) {
		ModelAndView mdv = new ModelAndView("user");
		Page<User> pageUserList = userService.findAllUser(PageRequest.of(page, 6));
		List<User> listUser = pageUserList.getContent();
		mdv.addObject("userList", listUser);
		return mdv;
	}
	
	
}
