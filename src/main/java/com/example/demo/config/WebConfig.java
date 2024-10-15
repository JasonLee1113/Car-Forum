package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/home").setViewName("home");
//		registry.addViewController("/").setViewName("home");
//		registry.addViewController("/login").setViewName("login");
//	}
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:8080")
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
//	                .allowedHeaders("*")
//	                .allowCredentials(true);
	    }
	
}
