package com.ecommerce.project.security.jwt;

import com.ecommerce.project.security.jwt.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${spring.app.jwtExpirationMs}")
    private Long jwtExpirationTimeInMS;


    @Value("${spring.app.jwt.cookieName}")
    private String jwtCookieName;

    // Getting JWT token from Header
//    public String getJWTTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization Header token :{}", bearerToken);
//        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
//            bearerToken = bearerToken.substring(7).trim();
//            return bearerToken;
//        }
//        return null;
//    }

    // Getting JWT token from Browser Cookie
    public String getJWTFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            logger.debug("Found JWT cookie :{}", cookie.getValue());
            return cookie.getValue();
        }
        return null;
    }

    // Generating/Building JWT token from Username
    public String generateJWTToken(String userName) {
        return Jwts.builder().subject(userName)
                .issuedAt(new Date()).
                expiration(new Date(new Date().getTime() + jwtExpirationTimeInMS))
                .signWith(getKey()).compact();
    }

    //// Generating/Building Cookie consist of JWT token from Username
    public ResponseCookie generateJWTCookies(UserDetailsImpl userDetails) {
        String jwtToken = generateJWTToken(userDetails.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, jwtToken).path("/api").maxAge(24 * 60 * 60).httpOnly(false).build();
        logger.info("Generated JWT Cookie : {}", cookie);
        logger.info("Generated JWT Cookie Name: {}", cookie.getName());
        return cookie;
    }

    //// Generating/Building Cookie consist of JWT token from Username
    public ResponseCookie getCleanJWTCookies() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null).path("/api").build();
        logger.info("JWT Cookie set null : {}", cookie);
        return cookie;
    }


    // Parsing Token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    // Getting Key
    Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validating Starts:");
            Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(authToken);
            System.out.println("Validated Successful");
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
