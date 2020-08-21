package com.yamaniha.cep.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {

	@Value("${security.jwt.header}")
	private String header;

	@Value("${security.jwt.prefix}")
	private String prefix;

	@Value("${security.jwt.secret}")
	private String secret;

	public String getHeader() {
		return header;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public String getSecret() {
		return secret;
	}
	
}

