package com.israelsantana.demo.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.israelsantana.demo.security.JWTAuthenticationFilter;
import com.israelsantana.demo.security.JWTAuthorizationFilter;
import com.israelsantana.demo.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JWTUtil jwtUtil;

        private static final String[] PUBLIC_MATCHERS = {
                        "/",
        };

        private static final String[] PUBLIC_MATCHERS_POST = {
                        "/user",
                        "/login"
        };

        private static final String[] PUBLIC_MATCHERS_GET = {
                "/webScraping","/webScraping/**",
                "/analyzes","/analyzes/**"

        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.cors(withDefaults()).csrf(csrf -> csrf.disable());

                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.userDetailsService(this.userDetailsService)
                                .passwordEncoder(bCryptPasswordEncoder());
                this.authenticationManager = authenticationManagerBuilder.build();

            http.authorizeRequests(requests -> requests
                    .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                    .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                    .antMatchers(PUBLIC_MATCHERS).permitAll()
                    .anyRequest().authenticated())
                    .authenticationManager(authenticationManager);

                http.addFilter(new JWTAuthenticationFilter(this.authenticationManager, this.jwtUtil));
                http.addFilter(new JWTAuthorizationFilter(this.authenticationManager, this.jwtUtil,
                                this.userDetailsService));

            http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
                configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
