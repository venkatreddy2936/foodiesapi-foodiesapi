package in.venkat.foodiesapi.util;


import in.venkat.foodiesapi.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {
    
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;



//    This is for user token only
//    public String generateToken(UserDetails userDetails){
//        Map<String, Objects> claiams=new HashMap<>();
//        return createToken(claiams, userDetails.getUsername());
//    }

    // ←— Public entry point: generates a token containing the user's role
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        // Include role claim, e.g. "ROLE_ADMIN" or "ROLE_USER"
        claims.put("role", "ROLE_" + user.getRole());
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000)) // 10h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
      public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
      }

      public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
      }
       public  <T> T extractClaim(String token, Function<Claims, T> ClaimsResolver){
        final Claims claims =extractAllCliams(token);
        return  ClaimsResolver.apply(claims);
       }
       private  Claims extractAllCliams(String token){
        return  Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
       }

       private Boolean isTokenExpired(String  token){
        return extractExpiration(token).before(new Date());
       }

       public Boolean validateToken(String  token, UserDetails  userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
       }




}
