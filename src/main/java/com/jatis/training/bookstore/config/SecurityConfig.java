package com.jatis.training.bookstore.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder encoder = new BCryptPasswordEncoder(11);

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(encoder)
			.withUser("admin").password(encoder.encode("passwordAdmin")).roles("ADMIN")
			.and().passwordEncoder(encoder)
			.withUser("cashier").password(encoder.encode("passwordCashier")).roles("CASHIER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if ("true".equals(System.getProperty("basicAuth", "false"))) {
			http = http.httpBasic().and();
		} else {
			http = http.formLogin().successHandler(new AuthenticationSuccessHandler() {
				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					response.getWriter().println("Welcome to bookstore!");
				}
			}).and();
		}
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/buy").hasRole("CASHIER")
		.antMatchers(HttpMethod.POST, "/book").hasRole("ADMIN")
		.anyRequest().authenticated();
	}

	@Bean
	public HttpSessionIdResolver httSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
}
