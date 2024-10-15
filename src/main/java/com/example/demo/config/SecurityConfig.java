package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(daoAuthenticationProvider());
	}
	
	 @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider 
	                                  = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService);
	        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
	        return authenticationProvider;
	    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http
				.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/home/**", "/login.html", "/signup.html").permitAll()
						.requestMatchers("/user/**").permitAll()
						
						.requestMatchers("/images/**").permitAll()
						.anyRequest().authenticated()
						)
				.build();
	}
	
	
	

}
