package it.uniroma3.siw.progetto.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.controller.session.SessionData;
import it.uniroma3.siw.progetto.controller.validator.UserValidator;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.RuoloService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class UserController
{
	@Autowired
	protected RuoloService ruoloService;

	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected SessionData sessionData;

	/* Funzione che si occupa di aggiungere ruolo e utente nel db se l'utente loggato
	 * si e' appena registrato e aggiunge entrambi al modello. */
	private void addUser(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		String idGit = principal.getAttribute("login");		//Prendo l'username dell'utente

		/* Cerco l'utente */
		Utente utente = this.utenteService.getUtente(idGit);
		Ruolo ruolo;

		/* Utente inesistente */
		if(utente==null)
		{
			/* Aggiungo utente e ruolo al database */
			utente = new Utente();
			ruolo = new Ruolo();

			utente.setUsername(idGit);
			utente.setMail(principal.getAttribute("email"));
			ruolo.setDefaultRole();
			ruolo.setUtente(utente);

			utente = this.utenteService.save(utente);
			ruolo = this.ruoloService.save(ruolo);
		}
		else //Utente già registrato
		{
			ruolo = this.ruoloService.getRuolo(utente);		//Prendo il ruolo
		}

		/* Aggiungo utente e ruolo al modello */
		model.addAttribute("utente", utente);
		model.addAttribute("ruolo", ruolo);
	}

	/* Prende la home dell'utente */
	@RequestMapping(value = { "/userHome" }, method = RequestMethod.GET)
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		
		/* Controllo che l'utente sia loggato */
		if(principal != null)
		{
			/* Se l'utente non è stato aggiunto al modello */
			if(!model.containsAttribute("utente"))
			{
				/* Eventualmente lo creo (se non esiste nel db) e lo aggiungo al modello */
				addUser(principal, model);
			}
		}
		
		/*Se il ruolo è ADMIN mi rimanda alla pagina dell'admin*/
		Ruolo ruolo = sessionData.getLoggedRole(principal);
		if(ruolo.getRuolo().equals("ADMIN")) 
		{
			return "admin";
		}

		return "home";
    }

	/* Prende il profilo dell'utente */
	@RequestMapping(value = { "/user/profile" }, method = RequestMethod.GET)
    public String profile(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		Utente utenteLoggato = sessionData.getLoggedUser(principal);
		Ruolo ruolo = sessionData.getLoggedRole(principal);
		model.addAttribute("utente", utenteLoggato);
		model.addAttribute("ruolo", ruolo);
		return "userProfile";
    }
	
	/* Mostra la form di aggiornamento del profilo */
	@RequestMapping(value = { "/editProfile" }, method = RequestMethod.GET)
	public String showFormUpdate(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		Utente utente = sessionData.getLoggedUser(principal);
		model.addAttribute("utente", utente);

		return "updateProfile";
	}
	
	/* Aggiorna il profilo dell'utente */ 
	@RequestMapping(value = { "/editProfile" }, method = RequestMethod.POST)
	public String updateUtente(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal, Model model)
	{
		UserValidator validator = new UserValidator();
		Ruolo ruolo = sessionData.getLoggedRole(principal);
		Utente utente = sessionData.getLoggedUser(principal);
		
		String comando = request.getParameter("submit");
		String nome = request.getParameter("nomeInput"); 
		String cognome = request.getParameter("cognomeInput");
	 	String mail = request.getParameter("mailInput");
	 	
	 	if(comando.equals("invia"))
	 	{
	 		/* Valido i dati inseriti */
			if(!validator.validate(request)) 
			{
				/* Dati non validi, aggiorno le informazioni dell'utente nel modello */
				utente.setNome(nome);
				utente.setCognome(cognome);
				utente.setMail(mail);
				model.addAttribute("utente", utente);
				
			 	return "updateProfile";
		 	}	
		 	else 
		 	{
		 		/* Dati validi, aggiorno le informazioni dell'utente nel db e nel modello */
			 	utente.setNome(nome);
			 	utente.setCognome(cognome);
			 	utente.setMail(mail);	 
			 	utenteService.save(utente);
		 	}
	 	}
	 
	 	model.addAttribute("utente", utente);
	 	model.addAttribute("ruolo", ruolo);
	 	return "userProfile";
	}
}
