package com.generic.core.base.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtility implements Serializable {
    //public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 60;
    
    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey("SECRETKEY").parseClaimsJws(token).getBody();
    }
    
    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public static String generateSignUpToken(UserDetailsModel userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken24hrs(claims, userDetails.getUsername());
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, "SECRETKEY").compact();
    }

    private static String doGenerateToken24hrs(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (24 * JWT_TOKEN_VALIDITY) * 1000)).signWith(SignatureAlgorithm.HS512, "SECRETKEY").compact();
    }
    
    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
