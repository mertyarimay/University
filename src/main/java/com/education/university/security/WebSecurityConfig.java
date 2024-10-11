package com.education.university.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //spring securitynin etkinleştirilmesini sağlar güvenlik yapılandırılmasını  kullanılabilir olmasını sağlar
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable()  //csrf koruması devre dışı bırakılmıştır
                )
                .authorizeHttpRequests(authz -> authz  //yetkikendirme kuralları tanımlanır
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        //yetkilendirme kuralları tanımlanır permitAll()  dememizin sebebi kimlik doğrulama istemez
                        .requestMatchers("/student/getAll").permitAll()
                        .requestMatchers("/section/getAll").permitAll()
                        .anyRequest().authenticated() //diğer tüm isteklerde  doğrulama istenir
                );
        return http.build();//Yapılandırmayı tamamlar ve SecurityFilterChain nesnesini oluşturur. Bu nesne, belirtilen güvenlik kurallarını uygulamak için kullanılacaktır.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}





