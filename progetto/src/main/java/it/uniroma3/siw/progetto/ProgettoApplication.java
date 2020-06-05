package it.uniroma3.siw.progetto;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class ProgettoApplication 
{
//	@GetMapping("/")
//	public String message(Principal principal) 
//	{
//		return "Hi "+principal.getName()+" welcome to MammitaApplication";
//	}

	public static void main(String[] args) 
	{
		SpringApplication.run(ProgettoApplication.class, args);
	}
}
