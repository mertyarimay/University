package com.education.university.security;

import com.education.university.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);  //tokene bearer başlığını çıkartıp alır

        if (token != null && jwtUtil.validateToken(token)) {  //tokenen geçerliliği kontrol edilir süresi dolmuşmu vs
            String username = jwtUtil.extractUsername(token); //token doğruysa username bilgisi  çıkarılır
            String role = jwtUtil.extractRole(token); //token doğruysa role  bilgisi  çıkarılır

            UsernamePasswordAuthenticationToken authentication =  //username kullanılarak bir kimlik doğrulama yapılır
                    new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList(role));
            SecurityContextHolder.getContext().setAuthentication(authentication); //spring security e bilgileri set etme hangi role sahip bu belirlenip yetkisi olan yerlere ulaşılması sağlanır
        }          //SecurityContext  sınıfı o kullanıcıya ait yetkileri tutar

        filterChain.doFilter(request, response); // Diğer filtrelere geç
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " kısmını çıkartıp token'ı döndür
        }
        return null;
    }
}


