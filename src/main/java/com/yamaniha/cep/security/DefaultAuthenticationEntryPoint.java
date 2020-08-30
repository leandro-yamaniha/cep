package com.yamaniha.cep.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;
import com.yamaniha.cep.dto.ErrorDto;
import com.yamaniha.cep.utils.RequestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
						final AuthenticationException authException) 
						throws IOException, ServletException {
		
		RequestUtils.logRequest(request);
		
		final String bodyResponseJson = buildUnAuthorizedResponse();
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(bodyResponseJson);

		MDC.put("http.responseBody", bodyResponseJson);		
		MDC.put("http.status", String.valueOf(response.getStatus()));
		
		log.info("request complete.");
		MDC.clear();

	}

	

	private String buildUnAuthorizedResponse() {
		return new Gson().toJson(new ErrorDto("acesso não autorizado"));
	}


}
