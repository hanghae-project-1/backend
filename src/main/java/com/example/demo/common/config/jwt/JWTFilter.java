package com.example.demo.common.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.domain.user.common.dto.CustomUserDetails;
import com.example.demo.domain.user.common.entity.Role;
import com.example.demo.domain.user.common.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response, FilterChain filterChain) throws
		ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if (isInvalidOrNonBearerAuthorization(authorization)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getToken(authorization);

		if (jwtUtil.isExpired(token)) {
			filterChain.doFilter(request, response);

			return;
		}
		setupUserAuthentication(getUser(token));
		filterChain.doFilter(request, response);
	}

	private String getToken(String authorization) {
		return authorization.split(" ")[1];
	}

	private void setupUserAuthentication(User user) {
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			customUserDetails, null, customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	private User getUser(String token) {
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);
		Role userRole = Role.valueOf(role.replace("ROLE_", ""));
		return User.builder()
			.username(username)
			.password("temppassword")
			.role(userRole)
			.build();
	}

	private boolean isInvalidOrNonBearerAuthorization(String authorization) {
		return authorization == null || !authorization.startsWith("Bearer ");
	}

}