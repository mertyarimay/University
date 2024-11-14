package com.education.university.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secret_key"; // JWT'leri doğrulamak için gizli bir anahtar
    private final long EXPIRATION_TIME = 3600000; // 1 saat geçerlilik süresi

    // Token oluşturma metodu
    public String generateToken(String username, String roleName) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String roleClaim = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return JWT.create()
                .withSubject(username) // Token'a kullanıcı adı atanır
                .withClaim("role", roleClaim) // Rol ismi token'a eklenir
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token'ın geçerlilik süresi belirlenir
                .sign(algorithm); // Token imzalanır
    }

    // Token'dan kullanıcı adını alma metodu
    public String extractUsername(String token) {
        DecodedJWT jwt = JWT.decode(token); // Token'ı decode ediyoruz yani payload kısmına bakılır yetkiler username vs
        return jwt.getSubject(); // Kullanıcı adını döndürüyoruz
    }

    // Token'dan kullanıcının rolünü çıkarma metodu
    public String extractRole(String token) {
        DecodedJWT jwt = JWT.decode(token); // Token'ı decode ediyoruz
        return jwt.getClaim("role").asString(); // Rol bilgisi alınır
    }

    // Token doğrulama metodu
    public boolean validateToken(String token) {
        try {
            // Token'ı doğrulamak için algoritma
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build(); // JWT doğrulayıcı oluşturulur
            DecodedJWT jwt = verifier.verify(token); // Token doğrulanır

            // Token süresi geçerli mi kontrol etme
            return !isTokenExpired(jwt);
        } catch (Exception e) {
            return false; // Eğer doğrulama başarısız olursa, false döner
        }
    }

    // Token'ın süresinin dolup dolmadığını kontrol etme metodu
    private boolean isTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().before(new Date()); // Geçerlilik süresi bitmişse false döner
    }

    // Token'ın geçerli olup olmadığını ve kullanıcı adı ve rolü doğrulama
    public boolean validateToken(String token, String username, String expectedRole) {
        try {
            // Algoritma ile JWT doğrulayıcı oluşturuluyor
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token); // Token doğrulama işlemi

            // Kullanıcı adı ve rol kontrolü
            String role = jwt.getClaim("role").asString();
            return jwt.getSubject().equals(username) && role.equals(expectedRole) && !isTokenExpired(jwt);
        } catch (Exception e) {
            return false; // Hata durumunda false döndürülür
        }
    }
}
