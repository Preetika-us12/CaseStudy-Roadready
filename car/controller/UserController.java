package com.springboot.car.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.car.model.User;
import com.springboot.car.service.UserService;
import com.springboot.car.util.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/signup")
	public User signUp(@RequestBody User user ) {
		return userService.signUp(user);
	}
	
	@GetMapping("/token")
	public ResponseEntity<?> getToken(Principal principal) {
		try {
		String token =jwtUtil.createToken(principal.getName()); 
		return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping("/details")
	public Object getLoggedInUserDetails(Principal principal) {
		String username = principal.getName();
		Object object = userService.getUserInfo(username);
		return object;
	}
}
