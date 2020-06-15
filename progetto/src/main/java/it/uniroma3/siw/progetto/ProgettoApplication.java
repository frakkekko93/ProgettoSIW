package it.uniroma3.siw.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProgettoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ProgettoApplication.class, args);
	}
}
