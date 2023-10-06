package com.gsoft.MyPgManagementApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsoft.MyPgManagementApplication.dto.User;
import com.gsoft.MyPgManagementApplication.responsestructure.ResponseStructure;
import com.gsoft.MyPgManagementApplication.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
		return service.saveUser(user);
	}

	@PatchMapping("/{otp}")
	public ResponseEntity<ResponseStructure<String>> verifyOtp(@PathVariable int otp){
		return service.verifyOtp(otp);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<String>> forgotPassword(@RequestParam String email){
		return service.forgotPassword(email);
	}
	
	@GetMapping("/{otp}/{password}")
	public ResponseEntity<ResponseStructure<String>> forgotPassword(@PathVariable int otp, @PathVariable String password){
		return service.reSetPassword(otp,password);
	}
	
	
	@GetMapping("/login")
	public ResponseEntity<ResponseStructure<User>> forgotPassword(@RequestParam String username, @RequestParam String password){
		return service.verifyUser(username, password);
	}
	
	
}
