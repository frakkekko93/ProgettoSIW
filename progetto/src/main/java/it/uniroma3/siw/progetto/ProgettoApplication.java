package it.uniroma3.siw.progetto;

import java.util.Collections;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
public class ProgettoApplication
{
	/* Provvede a recuperare l'username dell'utente per sostenere il javascript nell'index */
	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) 
	{
		return Collections.singletonMap("username", principal.getAttribute("login"));
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(ProgettoApplication.class, args);
	}
}
