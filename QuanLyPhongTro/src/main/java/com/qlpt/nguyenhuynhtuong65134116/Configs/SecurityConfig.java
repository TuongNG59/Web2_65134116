package com.qlpt.nguyenhuynhtuong65134116.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll() // Cho phép tất cả mọi người truy cập link Trang chủ ("/")
                // Các link CSS, JS, Hình ảnh (nếu có sau này) cũng được thả cửa
                .requestMatchers("/css/**", "/images/**", "/js/**").permitAll() 
                .anyRequest().authenticated() // Tất cả các link khác (như thuê phòng, quản lý) đều bắt buộc Đăng nhập
            )
            .formLogin(form -> form.permitAll()); // Vẫn giữ nguyên form đăng nhập mặc định của Spring

        return http.build();
    }
}
