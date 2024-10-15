package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetailsService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {

		try {

			System.out.println("signup");
			log.info("signup account:" + user.getUsername());
			log.info("signup password:" + user.getPassword());

			User findUser = userDetailsService.loadUserByUsername(user.getUsername());
			if (findUser != null) {
				throw new Exception("此帳號已存在 換一個!");
			}

			String encodePassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			userRepository.save(user);

			ApiResponse response = new ApiResponse(true, "註冊成功!");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(true, "註冊失敗!" + e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody User user) {

		try {
			log.info("login account:" + user.getUsername());
			log.info("login password" + user.getPassword());
			
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			ApiResponse response = new ApiResponse(true, "登入成功");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(true, "登入失敗!" + e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

//	@PostMapping("/signup")
//	public User signup(@RequestBody User user) {

//		System.out.println("userName:" + user.getUserName());
//		System.out.println("emailAddress:" + user.getEmailAddress());
//		System.out.println("passWord:" + user.getPassWord());

//		return userRepository.save(user);
//	}

//	@PostMapping("/login")
//	public User login(@RequestBody User user){
//		userService.login(user);
//		
//		
//	}

}
