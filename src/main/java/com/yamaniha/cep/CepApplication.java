package com.yamaniha.cep;

import java.net.SocketException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.yamaniha.cep.properties.JwtProperties;
import com.yamaniha.cep.utils.LogEnvUtils;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
public class CepApplication {
	
	CepApplication() {
		
	}
	
	public static void main(final String[] args) throws SocketException {
		
		LogEnvUtils.logInitialize(SpringApplication.run(CepApplication.class));
		
	}

}
