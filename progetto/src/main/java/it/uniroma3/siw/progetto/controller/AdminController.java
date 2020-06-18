package it.uniroma3.siw.progetto.controller;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.controller.session.SessionData;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.RuoloService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class AdminController 
{
	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected RuoloService ruoloService;
	
	@Autowired
	protected ProgettoService progettoService;
	
	@Autowired
	protected SessionData sessionData;

	/* Visualizza la lista di tutti gli utenti escluso l'admin loggato */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String userList(Model model, @AuthenticationPrincipal OAuth2User principal)
    {
		Utente utenteLoggato = this.sessionData.getLoggedUser(principal);
		List<Utente> utenti = this.utenteService.getAllUsers();

		Iterator<Utente> it = utenti.iterator();
		
		Utente u = null;
		while(it.hasNext())
		{
			/* Catturo un eccezione dovuta alla concorrenza sui thread */
			try
			{
				u = it.next();
			}
			catch(Exception e)
			{
				break;
			}
			
			if(u.getUsername().equals(utenteLoggato.getUsername()))
			{
				utenti.remove(u);
			}
		}
		
		model.addAttribute("utenti", utenti);
        
		return "userList";
    }
	
	/* Mostra il profilo dell'utente da eliminare */
	@RequestMapping(value= {"/viewUtente"}, method = RequestMethod.POST)
	public String showUtente(Model model, HttpServletRequest request)
	{	
		Utente utente = this.utenteService.getUtente(Long.parseLong(request.getParameter("utente")));
		Ruolo ruolo = this.ruoloService.getRuolo(utente);

		model.addAttribute("utente", utente);
		model.addAttribute("ruolo", ruolo);
		
		return "userToDelete";
	}
	
	/* Cancella un utente */
	@RequestMapping(value= {"/deleteUtente"}, method = RequestMethod.POST)
	public String deleteUtente(HttpServletRequest request, Model model)
	{
		Utente utente = this.utenteService.getUtente(Long.parseLong(request.getParameter("utente")));
		String comando = request.getParameter("submit");

		if(comando.equals("elimina")) 
		{   
		    /* Elimino utente e ruolo */
			utenteService.delete(utente);
		}
		
		List<Utente> utenti = this.utenteService.getAllUsers();
		model.addAttribute("utenti", utenti);		

		return "admin";
	}
}
