package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	// 首頁
	@GetMapping("/index")
	public String index() {
		System.out.println("test123");
		return "index";
	}
	
	
	@GetMapping("/signup")
	public String signup() {
		System.out.println("redirect:signuptest");
		
		return "redirect:/signup.html";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("redirect:login");
		return "redirect:/login.html";
	}

	
	
}
