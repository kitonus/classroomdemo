package com.jatis.training.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder encoder = new BCryptPasswordEncoder(11);

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(encoder)
			.withUser("admin").password(encoder.encode("passwordAdmin")).roles("ADMIN")
			.and()
			.withUser("cashier").password(encoder.encode("passwordCashier")).roles("CASHIER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and()
		.csrf().disable()
		.anonymous().disable()
		.authorizeRequests()
		.anyRequest().authenticated();	
	}

	@Bean
	public HttpSessionIdResolver httSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
}
