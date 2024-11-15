package com.example.demo.common.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		ServletRequestAttributes attributes =
			(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

		Optional<String> username = extractUsernameFromJoinRequest(attributes);
		if (username.isPresent()) {
			return username;
		}

		Authentication authentication =
			SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		return Optional.ofNullable(authentication.getName());
	}

	private Optional<String> extractUsernameFromJoinRequest(
		ServletRequestAttributes attributes) {
		if (attributes == null) {
			return Optional.empty();
		}

		HttpServletRequest request = attributes.getRequest();
		if (!isJoinUri(request)) {
			return Optional.empty();
		}

		String username = request.getParameter("username");
		return Optional.ofNullable(username);
	}

	private boolean isJoinUri(HttpServletRequest request) {
		return request.getRequestURI().endsWith("/api/v1/join")
			|| request.getRequestURI().endsWith("/api/v1/owner/join")
			|| request.getRequestURI().endsWith("/api/v1/manager/join")
			|| request.getRequestURI().endsWith("/api/v1/master/join");
	}

}
