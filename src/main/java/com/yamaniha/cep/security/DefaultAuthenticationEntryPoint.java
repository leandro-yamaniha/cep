package com.yamaniha.cep.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;
import com.yamaniha.cep.dto.ErrorDto;


public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
						final AuthenticationException authException) 
						throws IOException, ServletException {
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(buildUnAuthorizedResponse());

	}

	private String buildUnAuthorizedResponse() {
		return new Gson().toJson(new ErrorDto("acesso não autorizado"));
	}


}
