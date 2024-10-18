package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JwtAuthEntryPoint;
import com.example.demo.security.JwtAuthTokenFilter;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Autowired
	private JwtAuthEntryPoint  unauthorizedHandler;
	
	@Autowired
	private JwtAuthTokenFilter jwtAuthTokenFilter;
	
//	@Bean
//	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
//		return new JwtAuthTokenFilter();
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
//	@Bean
//	public AuthenticationManager authenticationManager() {
//		return new ProviderManager(daoAuthenticationProvider());
//	}
	
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
				.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/home/**", "/login.html", "/signup.html", "/navbar.html").permitAll()
						.requestMatchers("/user/**").permitAll()
						
						.requestMatchers("/images/**", "/css/**").permitAll()
						.anyRequest().authenticated()
						)
				.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	 

}
