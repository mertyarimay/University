package com.education.university.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize()  kullanabiliyoruz bu annotation sayesinde
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/register", "/auth/login", "/role/create").permitAll() // Kayıt ve giriş için herkes erişebilir
                        .requestMatchers("/section/**").hasAuthority("ROLE_ADMIN") // ROLE_ADMIN erişebilir
                        .requestMatchers("/student/getById/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER") //ROLE_ADMIN ve ROLE_USER Erişebilir
                        .requestMatchers("/student/add").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/student/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/student/update/{id}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/student/delete/{id}").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated() // Diğer tüm isteklere kimlik doğrulama zorunlu
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi ekliyoruz
         //jwtAuthenticationFilter ilk önce bu filtre uygulanır tokenen doğruluğu kontrol edilir ,UsernamePasswordAuthenticationFilter.class kimlik doğrulaması için kullanılır login işlemlerinde

        return http.build(); // Yapılandırmayı tamamla ve SecurityFilterChain nesnesini döndür
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
