package com.example.retakeManagement;

import com.example.retakeManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(getPasswordEncoder());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> {
            try {
                authorize
                        .requestMatchers("/cabinets/**","/courses/**",
                                "/departments/**", "/events/**", "/institutes/**",
                                "/retakes/**", "/specializations/**",
                                "/untreatedStudents/**", "/auth/registerAdmin",
                                "/profiles",
                                "/auth/profileInfo/**").hasRole("ADMIN")

                                .requestMatchers("/auth/login", "/error", "/swagger-ui/**",
                                        "/auth/register", "/js/**", "/css/**")
                                .permitAll()
                                .anyRequest().hasAnyRole("STUDENT", "ADMIN", "TEACHER").and()
                        .formLogin((form) -> form
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .defaultSuccessUrl("/auth/profile", true)
                                .failureUrl("/auth/login?error"))
                        .logout((logout) -> logout.logoutUrl("/auth/logout").
                                logoutSuccessUrl("/auth/login"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
