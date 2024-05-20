package com.project.MgShare.config.user;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "register","registerPass").permitAll() //制限なし
                        .requestMatchers("/admin").hasRole("ADMIN") //管理者
                        .requestMatchers("/info/**","/user/main").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        httpSecurity
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginPass")
                        .permitAll()
                );

        httpSecurity
                .csrf((auth) -> auth.disable());

        return httpSecurity.build();

    }

    @Bean //PW 暗号化
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
