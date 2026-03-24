package com.popo.esun.socialmedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 之前寫的密碼加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 這是新增的：設定安全攔截規則
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. 關閉 CSRF 防護：因為我們是 RESTful API，加上前後端分離，通常不需要這個
                .csrf(csrf -> csrf.disable())

                // 2. 設定路徑權限
                .authorizeHttpRequests(auth -> auth
                        // 暫時開放所有請求，方便我們先進行前端開發與 Postman 測試。
                        // 等到之後要實作 JWT 時，我們再來把這裡改成只放行 /api/users/register 和 login。
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}