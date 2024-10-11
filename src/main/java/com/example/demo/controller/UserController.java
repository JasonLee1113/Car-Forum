package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public User signup(@RequestBody User user) {
		
		System.out.println("userName:" + user.getUserName());
		System.out.println("emailAddress:" + user.getEmailAddress());
		System.out.println("passWord:" + user.getPassWord());

		
		return userRepository.save(user);
	}

//	@PostMapping("/login")
//	public User login(@RequestBody User user){
//		userService.login(user);
//		
//		
//	}
	
	
	
}
