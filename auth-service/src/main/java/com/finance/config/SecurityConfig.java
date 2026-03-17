package com.finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {



	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register", "/auth/refresh-token").permitAll()
                .anyRequest().authenticated()
        )
        .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
   }

	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	// 	http
	// 	.csrf(csrf -> csrf.disable())
	// 	.authorizeHttpRequests(auth -> auth
	// 					.requestMatchers("/auth/register", "/auth/login").permitAll()
	// 					.requestMatchers("/api/admin/**").hasRole("ADMIN")
	// 					.requestMatchers("/api/user/**").hasRole("USER")
	// 					.anyRequest().authenticated())
	// 	//.exceptionHandling(ex -> ex
	// 	//		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
	// 	//		.accessDeniedHandler(jwtAccessDeniedHandler))
	// 	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	// 	//.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		         

	// 	return http.build();
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
   
}
