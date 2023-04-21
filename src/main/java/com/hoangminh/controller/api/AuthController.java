package com.hoangminh.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoangminh.dto.LoginDTO;
import com.hoangminh.dto.ResponseDTO;
import com.hoangminh.dto.UserDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO info) {
		return null;
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO info) {
		return null;
	}
}
