package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Service
public class UserService {

	
	private UserDao userDao;
	
	public void login(User user) {
		userDao.login(user);
	}
}
