package com.project.MgShare.config.user;


import com.project.MgShare.helper.user.CustomAuthenticationSuccessHandler;
import com.project.MgShare.service.user.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "register","register/save","/js/**","/css/**").permitAll() //制限なし
                        .requestMatchers("/admin/**").hasRole("ADMIN") //管理者
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        httpSecurity
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .usernameParameter("userEmail")
                        .successHandler(customAuthenticationSuccessHandler())
//                        .defaultSuccessUrl("/user/main",true) //1st true
                        .failureUrl("/login?error=ture")
                        .permitAll()

                );

        httpSecurity
                .logout((auth) -> auth
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        httpSecurity
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(-1)
                        .userDetailsService(userSecurityService)
                );


        httpSecurity
                .csrf(AbstractHttpConfigurer::disable); //csrf on off

        return httpSecurity.build();

    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //PW 暗号化
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
