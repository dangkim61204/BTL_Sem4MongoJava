package com.example.ProjectSem4_JavaMongo;

import com.example.ProjectSem4_JavaMongo.Service.Impl.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.example.ProjectSem4_JavaMongo")
public class SercurityConfig {
    private final AccountDetailService accountDetailService;

    @Autowired
    public SercurityConfig(AccountDetailService accountDetailService) {
        this.accountDetailService = accountDetailService;
    }
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
        // SecurityFilterChain cho admin (/admin/** và /login)
        @Bean
        @Order(1)
        SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/admin/**", "/login")
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/admin/**").hasAuthority("ADMIN")
                            .requestMatchers("/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/login") // Trang đăng nhập cho admin
                            .loginProcessingUrl("/login") // URL xử lý đăng nhập
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(customAuthenticationSuccessHandler)
                            .failureUrl("/login?error=true") // Đăng nhập thất bại
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/admin/logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll()
                    );

            return http.build();
        }

        // SecurityFilterChain cho user (/user/**, /dang-nhap, và các trang công khai)
        @Bean
        @Order(2)
        public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/", "/home", "/dang-nhap", "/registerUser/**","/shop/**", "/product/**",
                                    "/css/**", "/js/**", "/images/**", "/product_detail/**", "/cart/**", "/checkout"
                            ).permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/dang-nhap")
                            .loginProcessingUrl("/dang-nhap")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/home", true)
                            .failureUrl("/dang-nhap?error=true")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/dang-nhap?logout=true")
                            .permitAll()
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/error")
                    );

            return http.build();
        }



    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/assets/**", "/uploads/**", "/users-assets/**");
    }
}