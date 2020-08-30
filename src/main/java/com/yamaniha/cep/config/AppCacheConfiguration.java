package com.yamaniha.cep.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@EnableCaching
@Configuration
public class AppCacheConfiguration {

	@Bean
	public Caffeine caffeineConfig() {
	    return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES);
	}
		
	@Bean
	public CacheManager cacheManager(final Caffeine caffeine) {
	    final CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
	    caffeineCacheManager.setCaffeine(caffeine);
	    return caffeineCacheManager;
	}
	
}
