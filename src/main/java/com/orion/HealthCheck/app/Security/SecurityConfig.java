package com.orion.HealthCheck.app.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http

                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests()
                .antMatchers("/users/reset-password").permitAll()
                .antMatchers("/users/forgot-password").permitAll()
                .antMatchers("/users/reset-password/**").permitAll()
                .antMatchers("/users/forgot-password/**").permitAll()
//                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
//                .antMatchers(HttpMethod.POST).authenticated()
//                .antMatchers(HttpMethod.PUT).hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.DELETE).hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers(HttpMethod.GET).authenticated()
                // .antMatchers("/users/test").hasAuthority("ADMIN")
//                .antMatchers("/", "index", "/css/*", "/js/*").hasAnyRole(ADMIN.name(), USER.name())
//                .antMatchers("/v1/**").hasRole(USER.name())
//                .antMatchers("/v1/**").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/users/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/v1/**").hasAuthority(UserPermission.LAB_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/**").hasAuthority(UserPermission.LAB_ADD.getPermission())
//                .antMatchers(HttpMethod.PUT, "/**").hasAuthority(UserPermission.LAB_UPDATE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority(UserPermission.LAB_DELETE.getPermission())
                .anyRequest().permitAll()
                .and()
                .httpBasic();


        http.headers().frameOptions().sameOrigin();

        return http.build();
    }
}