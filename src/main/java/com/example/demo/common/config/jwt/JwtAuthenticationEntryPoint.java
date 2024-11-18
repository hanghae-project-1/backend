package com.example.demo.common.config.jwt;

import com.example.demo.common.exception.Error;
import com.example.demo.common.model.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

		Response<Void> customResponse = Response.<Void>builder()
				.code(Error.UNAUTHORIZED.getCode())
				.message(Error.UNAUTHORIZED.getMessage())
				.build();

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String json = new ObjectMapper().writeValueAsString(customResponse);

		response.getWriter().write(json);
		response.getWriter().flush();

	}
}
