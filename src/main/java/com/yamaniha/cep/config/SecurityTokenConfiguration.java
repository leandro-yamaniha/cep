package com.yamaniha.cep.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.yamaniha.cep.properties.JwtProperties;
import com.yamaniha.cep.security.DefaultAuthenticationEntryPoint;
import com.yamaniha.cep.security.JwtTokenAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityTokenConfiguration extends WebSecurityConfigurerAdapter {
		
	private JwtProperties jwtConfig;
	
	SecurityTokenConfiguration(final JwtProperties jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling().authenticationEntryPoint(new DefaultAuthenticationEntryPoint()).and()
			.authorizeRequests().antMatchers(
					"/swagger-resources/**", "/v2/api-docs/**", "/csrf/**",
					"/webjars/**", "/swagger-ui.html", "/h2-console/**", 
					"/actuator/health", "/actuator/info")
			.permitAll().anyRequest().authenticated();
		
		http.headers().frameOptions().disable();
		
		http.addFilter(new JwtTokenAuthenticationFilter(authenticationManager(), jwtConfig));

	}

	
}
