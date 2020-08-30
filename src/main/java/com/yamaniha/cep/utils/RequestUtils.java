package com.yamaniha.cep.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

public class RequestUtils {

	RequestUtils() {
		
	}
	
	public static void logRequest(final HttpServletRequest request) throws IOException {
		final BufferedRequestWrapper bufferedRequest = 
				new BufferedRequestWrapper(request);		

        final Map<String, String> requestMap = RequestUtils.getTypesafeRequestMap(bufferedRequest);
        
		final Enumeration<String> requestHeaderNames = bufferedRequest.getHeaderNames();
		final Map<String, String> requestHeaders = RequestUtils
						.getRequestHeaders(bufferedRequest, requestHeaderNames);
		
		MDC.put("http.request.parameters", RequestUtils.getParameters(requestMap));			
		MDC.put("http.path", bufferedRequest.getServletPath());
		MDC.put("http.request.body", bufferedRequest.getRequestBody());
		MDC.put("http.headers", requestHeaders == null ? "" : requestHeaders.toString());
		MDC.put("http.method", bufferedRequest.getMethod());
	}
	
	public static void logResponse(final BufferedResponseWrapper bufferedResponse) {
		
		MDC.put("http.responseBody", bufferedResponse.getContent());		
		MDC.put("http.status", String.valueOf(bufferedResponse.getStatus()));
		
	}
	
	public static String getParameters(final Map<String, String> requestMap) {

		String parameters;
		if (requestMap != null) {
			parameters = requestMap.toString();
			if (parameters.substring(0, 1).equals("{")) {
				parameters = parameters.substring(1, parameters.length() - 1);
			}
			return parameters;

		}
		return null;

	}

	public static Map<String, String> getRequestHeaders(final BufferedRequestWrapper bufferedRequest,
			final Enumeration<String> requestHeaderNames) {
		Map<String, String> requestHeaders = null;
		if (requestHeaderNames != null) {
			requestHeaders = new HashMap<>();
			while (requestHeaderNames.hasMoreElements()) {
				final String headerName = requestHeaderNames.nextElement();
				requestHeaders.put(headerName, bufferedRequest.getHeader(headerName));
			}
		}
		return requestHeaders;
	}

	public static Map<String, String> getTypesafeRequestMap(final BufferedRequestWrapper bufferedRequest) {

		final Map<String, String> typesafeRequestMap = new HashMap<>();
		final Enumeration<?> requestParamNames = bufferedRequest.getParameterNames();
		while (requestParamNames.hasMoreElements()) {
			final String requestParamName = (String) requestParamNames.nextElement();
			final String requestParamValue = bufferedRequest.getParameter(requestParamName);
			typesafeRequestMap.put(requestParamName, requestParamValue);
		}
		return typesafeRequestMap;

	}
	
}
