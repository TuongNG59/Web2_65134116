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
                // Mở cửa cho Trang chủ, Đăng nhập, Đăng ký
                .requestMatchers("/", "/dangnhap", "/dangky", "/error").permitAll() 
                .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/thue-phong/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/dangnhap") // Chỉ định đường dẫn tới trang đăng nhập tự làm
                .loginProcessingUrl("/xuly-dangnhap") // Link ảo để Spring tự động bắt username/password
                .defaultSuccessUrl("/", true) // Đăng nhập thành công thì quay về Trang chủ
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/dangxuat") // Link ảo để đăng xuất
                .logoutSuccessUrl("/") // Đăng xuất xong về Trang chủ
                .permitAll()
            );
        	
        return http.build();
    }
    
    @Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
