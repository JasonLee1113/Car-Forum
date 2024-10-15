package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepository.findByUsername(username);
		
		return user;
	}
	
	
	
	
	
	
	

	
	
	
}
