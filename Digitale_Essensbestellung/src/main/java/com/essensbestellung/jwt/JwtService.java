package com.essensbestellung.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.essensbestellung.entities.User;
import com.essensbestellung.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	
	public static final String SECRET_KEY = "5N+6yAw9UJlZGIE3ivXxkQlxnb9BauSkvcdSJ447DQE=";

	public String generateToken(User user) {
		
		Role role = user.getRole();

		Map<String, Object> claimsMap =  new HashMap<>();
		claimsMap.put("role",role);
		
		return Jwts.builder()
		.setSubject(user.getUsername())
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2))
		.addClaims(claimsMap)
		.signWith(getKey(), SignatureAlgorithm.HS256)
		.compact();
	}
	
	
	public  Object getClaimsByKey(String token , String key) {
		Claims claims =  getClaims(token);
		return claims.get(key);
	}
	
	
	public  Claims getClaims(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token).getBody();
		return claims;
	}
	
	public <T> T exportToken(String token , Function<Claims, T> claimsFunction) {
		Claims claims =  getClaims(token);
		return claimsFunction.apply(claims);
	}
	
	public String getUsernameByToken(String token) {
		return exportToken(token, Claims::getSubject);
	}
	
	
	public boolean isTokenExpired(String token) {
		Date expiredDate= exportToken(token, Claims::getExpiration);
		return new Date().before(expiredDate);
	}
	
	public Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
