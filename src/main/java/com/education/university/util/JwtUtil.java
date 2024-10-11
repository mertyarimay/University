package com.education.university.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {   //jwt ile ilgili işlemleri bu sınıfta yapıyoruz

    private final String SECRET_KEY = "your_secret_key"; //jwt leri doğrulamak için gizli bir anahtar
    private final long EXPIRATION_TIME = 3600000; // 1 saat  tokenen geçerlilik süresi

    // Token oluşturma metodu
    public String generateToken(String username) {   //belirtilen kullanıcı adıyla token oluşturur
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()   //bir jwt oluşturur
                .withSubject(username)   //o tokena kullanıcı adı atanır
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))   //tokena exprıratıon atarak süresi belirlenir
                .sign(algorithm);
    }

    // Token'dan kullanıcı adını alma metodu
    public String extractUsername(String token) {  //verilen tokendan kullanıcı adı çıkartır
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();    //verilen tokendan kullanıcı adını alır ve döner
    }

    // Token doğrulama metodu
    public boolean validateToken(String token, String username) {   //tokenen geçerli olup olmadığını kontrol eder
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();//yukarda belirtilen algoritma ile bir jwt  doğrulayıcı oluşturulur
        DecodedJWT jwt = verifier.verify(token);  //Verilen tokene doğrular çözümler
        return (jwt.getSubject().equals(username) && !isTokenExpired(jwt));  //Çözümlenen token'daki kullanıcı adının, parametre olarak verilen kullanıcı adıyla eşleşip eşleşmediğini kontrol eder.
        //!isTokenExpired(jwt))  tokenen süresinin dolup dolmadığına bakar geçerliysede true döner yoksa false döner
    }

    // Token'ın süresinin dolup dolmadığını kontrol etme metodu
    private boolean isTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().before(new Date());
    }
}
