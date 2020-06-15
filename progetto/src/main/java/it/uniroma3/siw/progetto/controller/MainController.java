package it.uniroma3.siw.progetto.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController
{
	/* Effettua il logout dell'utente */
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) throws ServletException 
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        
        return "index";
	}
}
