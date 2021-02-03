package io.my.springsecurityjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    private static final String SECRET_KEY = "secret";

    public static String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public static Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public static <T>T extractClaim(String token, Function<Claims, T> claimsResolver) {
       final Claims claims = extractAllClaim(token);
       return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaim(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String jwt){
        return extractExpiration(jwt).before(new Date());
    }

    public static String generateToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }


    public static String createToken(Map<String, Object> claims, String subject){
       return Jwts.builder().setClaims(claims)
               .setSubject(subject)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public static boolean validateToken(String jwt, UserDetails user){
        String username = extractUsername(jwt);
        return (username.equals(user.getUsername()) && !isTokenExpired(jwt));
    }

}
