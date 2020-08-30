package com.yamaniha.cep.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogEnvUtils {

	LogEnvUtils() {
		
	}
	
	public static void logInitialize(final ConfigurableApplicationContext app) throws SocketException {
		
		final var env = app.getEnvironment();
		for (final NetworkInterface ni :
			Collections.list(NetworkInterface.getNetworkInterfaces())) {
			for (final InetAddress address : Collections.list(ni.getInetAddresses())) {
				logAddress(env, address);
			}
		}		
		logAppVersion(env);
		
	}

	public static void logAppVersion(final ConfigurableEnvironment env) {
		log.info("{} version {} - is running ... profile {}  ",
				env.getProperty("spring.application.name"),
				env.getProperty("info.build.version"),
				getActiveProfile(env)
				);
	}

	public static String getActiveProfile(final ConfigurableEnvironment env) {
		
		return env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "";
		
	}

	public static void logAddress(final ConfigurableEnvironment env, final InetAddress address) {
		
		if ((address instanceof Inet4Address) && !address.isLoopbackAddress()) {
			log.info("http:/{}:{}{}swagger-ui.html",
					address,
					env.getProperty("server.port"),
					env.getProperty("server.servlet.contextPath"));
		}
		
	}
	
}
