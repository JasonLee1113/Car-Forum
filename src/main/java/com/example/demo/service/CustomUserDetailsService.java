package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static List<User> users = new ArrayList<>();
	
	
	

	
	
	
}
