package com.yamaniha.cep;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CepApplication {
	
	CepApplication(){
		
	}
	
	public static void main(String[] args) throws SocketException {
		
		logInitialize(SpringApplication.run(CepApplication.class, args));
		
	}

	private static void logInitialize(ConfigurableApplicationContext app) throws SocketException {
		
		var env = app.getEnvironment();
		for (final NetworkInterface ni :
			Collections.list(NetworkInterface.getNetworkInterfaces())) {
			for (final InetAddress address : Collections.list(ni.getInetAddresses())) {
				logAddress(env, address);
			}
		}		
		logAppVersion(env);
		
	}

	private static void logAppVersion(ConfigurableEnvironment env) {
		log.info("{} version {} - is running ... profile {}  " ,
				env.getProperty("spring.application.name"),
				env.getProperty("info.build.version"),
				getActiveProfile(env)
				);
	}

	private static String getActiveProfile(ConfigurableEnvironment env) {
		
		return env.getActiveProfiles().length>0 ? env.getActiveProfiles()[0] : "";
		
	}

	private static void logAddress(ConfigurableEnvironment env, final InetAddress address) {
		if ((address instanceof Inet4Address) && !address.isLoopbackAddress())
		{
			log.info("http:/{}:{}{}swagger-ui.html",
					address,
					env.getProperty("server.port"),
					env.getProperty("server.servlet.contextPath"));
		}
	}

}
