package com.yamaniha.cep.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
	
	private final String header;	
	private final String prefix;	
	private final String secret;
	
	public JwtProperties(final String header, final String prefix, final String secret) {
		super();
		this.header = header;
		this.prefix = prefix;
		this.secret = secret;
	}
	
}

