package com.example.demo.common.config;

import com.example.demo.common.config.jwt.JWTFilter;
import com.example.demo.common.config.jwt.JWTUtil;
import com.example.demo.common.config.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	private final JWTUtil jwtUtil;

	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((auth) -> auth.disable());
		http.formLogin((auth) -> auth.disable());
		http.httpBasic((auth) -> auth.disable());
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/login", "/api/v1/*/join", "/api/v1/join",
						"/api-docs/**", "/swagger-ui/**",
						"/v3/api-docs/**",
						"/swagger-resources/**")
				.permitAll()
				.requestMatchers("/api/v1/store/**")
				.hasAnyRole("MANAGER", "OWNER", "MASTER")
				.requestMatchers("/api/v1/category/**")
				.hasAnyRole("MANAGER", "OWNER", "MASTER")
				.requestMatchers("/api/v1/region/**")
				.hasAnyRole("MANAGER", "OWNER", "MASTER")
				.requestMatchers("/api/v1/order/**")
				.authenticated()
				.requestMatchers("/api/v1/review/**")
				.hasAnyRole("", "MANAGER", "OWNER", "MASTER")
				.requestMatchers("/api/v1/review/**")
				.hasAnyRole("", "MANAGER", "OWNER", "MASTER")
				.anyRequest()
				.authenticated());
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		http.addFilterAt(
				new LoginFilter(authenticationManager(authenticationConfiguration),
						jwtUtil),
				UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.exceptionHandling(exceptionHandling -> exceptionHandling
				.accessDeniedHandler(jwtAccessDeniedHandler)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		);

		return http.build();
	}
}