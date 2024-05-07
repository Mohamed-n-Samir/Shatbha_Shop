package com.shatbha_shop.shatbha_shop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shatbha_shop.shatbha_shop.Services.UserService;
import com.shatbha_shop.shatbha_shop.security.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserService userService;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final CustomLogoutHandler logoutHandler;

	public SecurityConfig(UserService userService,
			JwtAuthenticationFilter jwtAuthenticationFilter,
			CustomLogoutHandler logoutHandler) {
		this.userService = userService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.logoutHandler = logoutHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers("/api/**")
								.permitAll()
								.requestMatchers("/api/dashboard/**").hasAuthority("admin")
								.anyRequest()
								.authenticated())
				.userDetailsService(userService)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(
						e -> e.accessDeniedHandler(
								(request, response, accessDeniedException) -> response
										.setStatus(403))
								.authenticationEntryPoint(new HttpStatusEntryPoint(
										HttpStatus.UNAUTHORIZED)))
				.logout(l -> l
						.logoutUrl("/api/logout")
						.addLogoutHandler(logoutHandler)
						.logoutSuccessHandler((request, response, authentication) -> {
							response.getWriter().write("تم تسجيل الخروج بنجاح");
							SecurityContextHolder.clearContext();
						}

						))
				.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
