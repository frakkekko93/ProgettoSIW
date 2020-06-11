package it.uniroma3.siw.progetto.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthConfiguration extends WebSecurityConfigurerAdapter 
{
	/* Configurazione dell'autenticazione dell'app */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		// @formatter:off
		http
			.authorizeRequests(a -> a
				.antMatchers("/", "/error", "/webjars/**").permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			)
			.oauth2Login();
		// @formatter:on
	}
}
