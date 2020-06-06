package it.uniroma3.siw.progetto.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.RuoloService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
@RequestMapping("/")
public class MainController
{
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("utente") != null)
		{
			RuoloService rs = new RuoloService();
			UtenteService us = new UtenteService();
			
			String idGit = principal.getAttribute("name");
			
			Ruolo r = rs.getRuolo(idGit);
			Utente u = us.getUtente(r.getUser().getId());
			
			session.setAttribute("utente", u);
			session.setAttribute("ruolo", r);
		}
		
		return "index";
    }

}
