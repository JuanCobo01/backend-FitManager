package com.uceva.fitmanager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.inactivity-timeout}")
    private long inactivityTimeout;

    // Almacena la última actividad de cada usuario
    private final Map<String, Long> userLastActivity = new ConcurrentHashMap<>();

    // Almacena tokens activos
    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();

    public String generateToken(String email, String userType, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .subject(email)
                .claim("userType", userType) // usuario, entrenador, administrador
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();

        // Registrar token activo y actividad
        activeTokens.put(email, token);
        updateUserActivity(email);

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String email = claims.getSubject();

            // Verificar si el token está en la lista de activos
            if (!activeTokens.containsKey(email) || !activeTokens.get(email).equals(token)) {
                return false;
            }

            // Verificar inactividad del usuario
            if (isUserInactive(email)) {
                invalidateUserToken(email);
                return false;
            }

            // Actualizar actividad del usuario
            updateUserActivity(email);
            return true;

        } catch (ExpiredJwtException e) {
            String email = e.getClaims().getSubject();
            invalidateUserToken(email);
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserType(String token) {
        return extractAllClaims(token).get("userType", String.class);
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public void updateUserActivity(String email) {
        userLastActivity.put(email, System.currentTimeMillis());
    }

    public boolean isUserInactive(String email) {
        Long lastActivity = userLastActivity.get(email);
        if (lastActivity == null) {
            return true;
        }
        return (System.currentTimeMillis() - lastActivity) > inactivityTimeout;
    }

    public void invalidateUserToken(String email) {
        activeTokens.remove(email);
        userLastActivity.remove(email);
    }

    public void invalidateAllTokens() {
        activeTokens.clear();
        userLastActivity.clear();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Método para obtener información del usuario actual desde el token
    public UserTokenInfo getCurrentUserInfo(String token) {
        Claims claims = extractAllClaims(token);
        return UserTokenInfo.builder()
                .email(claims.getSubject())
                .userType(claims.get("userType", String.class))
                .userId(claims.get("userId", Long.class))
                .build();
    }

    // Verificar si un usuario específico tiene token activo
    public boolean hasActiveToken(String email) {
        return activeTokens.containsKey(email) && !isUserInactive(email);
    }
}
