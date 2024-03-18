package ru.prodcontest.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ru.prodcontest.domain.user.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String extractLogin(String token) {
        return (String) exctractAllClaims(token).get("login");
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String login = extractLogin(token);
        String password = (String) exctractAllClaims(token).get("password");

        return login.equals(userDetails.getUsername()) &&
                password.equals(userDetails.getPassword())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractSpecClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractSpecClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = exctractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims exctractAllClaims(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("login", user.getUsername());
        claims.put("password", user.getPassword());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
