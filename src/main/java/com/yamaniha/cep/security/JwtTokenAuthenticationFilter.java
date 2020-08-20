package com.yamaniha.cep.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.yamaniha.cep.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {

	private final JwtConfig jwtConfig;

	public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		super(authenticationManager);
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(jwtConfig.getHeader());

		if (header != null && header.startsWith(jwtConfig.getPrefix())) {
			String token = header.replace(jwtConfig.getPrefix(), "");

			try {
				Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token)
						.getBody();

				String username = claims.getSubject();
				if (username.equals("cep-manager")) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							Arrays.asList(new SimpleGrantedAuthority("admin")));

					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				else {
					SecurityContextHolder.clearContext();
				}

			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(request, response);
	}

}
