package com.example.demo.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String jwtSecret = "AlohomoraIsASpellUsedToOpenDoors";
	private final int jwtExpirationMs = 432000000;

	// create JWT
	public String generateTwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(key).compact();
	}

	// 從JWT 中取得用戶名
	public String getUserNameFromJwtToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Authorise JWT
	public boolean validateJwtToken(String authToken) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);

			return true;
		} catch (MalformedJwtException e) {
			log.error("驗證JWT Exception: Malformed JWT " + e.toString());
			throw new JwtException("無效的JWT，請重新登入");
		} catch (ExpiredJwtException e) {
			log.error("驗證JWT Exception: JWT過期:" + e.toString());
			throw new JwtException("登入逾時，請重新登入");
		} catch (UnsupportedJwtException e) {
			log.error("驗證JWT Exception: Unsupported JWT:" + e.toString());
			throw new JwtException("不支持的JWT");
		} catch (IllegalArgumentException e) {
			log.error("驗證JWT Exception: JWT為空 - " + e.getMessage());
			throw new JwtException("JWT為空，請重新登入");
		}
	}
}
