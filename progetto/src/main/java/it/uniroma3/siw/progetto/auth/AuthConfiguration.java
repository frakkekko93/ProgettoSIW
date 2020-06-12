package it.uniroma3.siw.progetto.auth;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthConfiguration extends WebSecurityConfigurerAdapter 
{
	@Autowired
	DataSource datasource;
	
	/* Configurazione dell'autenticazione dell'app */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
//		// @formatter:off
//				http
//					.authorizeRequests(a -> a
//						.antMatchers("/", "/error", "/webjars/**").permitAll()
//						.anyRequest().authenticated()
//					)
//					.exceptionHandling(e -> e
//						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//					)
//					.oauth2Login();
//				// @formatter:on
		
		http.authorizeRequests()
		    .antMatchers("/", "/error", "/webjars/**").permitAll()
			.antMatchers(HttpMethod.GET, "/", "/index").permitAll()
			.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority("ADMIN")
			.anyRequest().authenticated()
			.and().csrf().disable()
			.oauth2Login();
	}
	
	/* Configurazione delle query per recuperare i ruoli degli utenti */
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception 
    {
        auth.jdbcAuthentication().dataSource(this.datasource)
                //prende username e ruolo
                .authoritiesByUsernameQuery("SELECT utente.username, ruolo.ruolo FROM ruolo INNER JOIN utente ON ruolo.utente_id=utente.id WHERE utente.username=?")
                //prende l'username
                .usersByUsernameQuery("SELECT username, 1 as enabled FROM utente WHERE username=?");
    }
}
