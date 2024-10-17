package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	
	@Autowired
	private JwtUtil jwtUtil;
	


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

			ApiResponse response = new ApiResponse(true, "註冊成功!", null);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(true, "註冊失敗!" + e.toString(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody User user, HttpServletRequest request) {

		try {
			log.info("login account:" + user.getUsername());
			log.info("login password" + user.getPassword());
			
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwt = jwtUtil.generateTwtToken(authentication);
			log.info("jwt:" + jwt);
			
			ApiResponse response = new ApiResponse(true, "登入成功", jwt);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(true, "登入失敗!" + e.toString(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(HttpServletRequest request){
		try {
		
			
			SecurityContextHolder.clearContext();
			ApiResponse response = new ApiResponse(true, "登出成功", null);
			return ResponseEntity.ok(response);
			
		}catch(Exception e) {
			log.error("例外訊息:" + e.toString());
			ApiResponse response = new ApiResponse(true, "登入失敗!" + e.toString(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	




}
