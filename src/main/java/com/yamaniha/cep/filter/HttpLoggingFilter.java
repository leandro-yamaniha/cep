package com.yamaniha.cep.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import com.yamaniha.cep.utils.BufferedRequestWrapper;
import com.yamaniha.cep.utils.BufferedResponseWrapper;
import com.yamaniha.cep.utils.RequestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpLoggingFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		
		final Long initialTime = System.currentTimeMillis();

		try {		
            
			final BufferedRequestWrapper bufferedRequest = 
					new BufferedRequestWrapper((HttpServletRequest) request);
			
			final BufferedResponseWrapper bufferedResponse = 
					new BufferedResponseWrapper((HttpServletResponse) response);
			
			RequestUtils.logRequest(bufferedRequest);
			
			chain.doFilter(bufferedRequest, bufferedResponse);
		
			RequestUtils.logResponse(bufferedResponse);
			
			MDC.put("http.latency", String.valueOf(System.currentTimeMillis() - initialTime));
			
			log.info("request complete.");			

		} catch (final Exception a) {
			log.error("request exception {}", a.getMessage());
		} finally {
			MDC.clear();
		}
	}

	


	
}
