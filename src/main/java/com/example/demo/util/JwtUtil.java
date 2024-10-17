package com.example.demo.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String jwtSecret = "AlohomoraIsASpellUsedToOpenDoors";
	private final int jwtExpirationMs = 10000;
	
	// create JWT
	public String generateTwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key)
				.compact();
	}
	
	// 從JWT 中取得用戶名
	public String getUserNameFromJwtToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody().getSubject();
	}
	
	// Authorise JWT
	public boolean validateJwtToken(String authToken) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		}catch(Exception e) {
			log.error("驗證JWT Exception:" + e.toString());
		}
		return false;
	}
	
}
