package com.finance.utils;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private final String SECRET = "mysecretkeymysecretkeymysecretkey";
	private final long EXPIRATION = 1000 * 60 * 60 * 24;

	private java.security.Key getSignKey() {
		return (java.security.Key) Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	public String generateToken(String email, Set<String> roles) {
		System.out.println("Auth Service date " +new Date());
		return Jwts.builder()
				.setSubject(email)
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();

	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	

	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
		
		return extractAllClaims(token).get("roles", List.class);
	}

	public boolean isTokenValid(String token) {
		try {
			System.out.println("Auth service date "+new Date());
			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
