package it.uniroma3.siw.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	/* Funzione che si occupa di aggiungere ruolo e utente nel db se l'utente loggato
	 * si e' appena registrato e aggiunge entrambi al modello.
	 */
	private void addUser(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		String idGit = principal.getAttribute("login");		//Prendo l'username dell'utente

		/* Cerco l'utente */
		Utente u = this.utenteService.getUtente(idGit);
		Ruolo r;

		/* Utente insesitente */
		if(u==null)
		{
			/* Aggiungo utente e ruolo al database */
			u = new Utente();
			r = new Ruolo();

			u.setUsername(idGit);
			u.setMail(principal.getAttribute("email"));
			r.setDefaultRole();
			r.setUser(u);

			u = this.utenteService.save(u);
			r = this.ruoloService.save(r);
		}
		else //Utente già registrato
		{
			r = this.ruoloService.getRuolo(u);		//Prendo il ruolo
		}

		/* Aggiungo utente e ruolo al modello */
		model.addAttribute("utente", u);
		model.addAttribute("ruolo", r);
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

		return "home";
    }

	/* Prende il profilo dell'utente */
	@RequestMapping(value = { "/user/profile" }, method = RequestMethod.GET)
    public String profile()
	{
		return "userProfile";
    }
}
