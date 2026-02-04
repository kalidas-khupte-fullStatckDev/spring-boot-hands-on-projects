package com.bank.app.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${spring.jwt.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${spring.jwt.jwtExpirationTimeInMS}")
    private Long jwtExpirationTimeInMS;

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    //  get JWT token from request
    public String retrieveJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authentication");
        if (token != null && token.startsWith("Bearer ")) {
            return token.trim().substring(7);
        }
        return null;
    }

    // generate JWT token
    public String generateJWTFromUserDetails(UserDetails userDetails) {
       return Jwts.builder().issuedAt(new Date()).expiration(new Date(new Date().getTime() + jwtExpirationTimeInMS)).signWith(getKey()).compact();
    }

    public SecretKey getKey() {
        //return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            System.out.println("Validating JWT token");
           // Jws<Claims> claims = Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token);
            Jws<Claims> claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            if (claims != null) {
                System.out.println("JWT is valid");
            }
        } catch (AuthenticationException e) {
            logger.debug("AuthenticationException occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }

    public String getUserNameFromToken(String token){
       // return  Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload().getSubject();
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }


}
