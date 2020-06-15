package it.uniroma3.siw.progetto.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.RuoloService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class AdminController {
	
	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected RuoloService ruoloService;

	/* Visualizza la lista di tutti gli utenti  */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String deleteUser(Model model)
    {

		List<Utente> utenti = this.utenteService.getAllUsers();
		model.addAttribute("utenti", utenti);
        
		return "userList";
    }
	
	/* Mostra il profilo dell'utente */
	@RequestMapping(value= {"/viewUtente"}, method = RequestMethod.POST)
	public String showUtente(Model model, HttpServletRequest request)
	{
		
		Utente utente = this.utenteService.getUtente(Long.parseLong(request.getParameter("utente")));
		Ruolo ruolo = this.ruoloService.getRuolo(utente);

		model.addAttribute("utente", utente);
		model.addAttribute("ruolo", ruolo);
		
		

		return "userToDelete";
	}
	
	/* Cancella un progetto */
	@RequestMapping(value= {"/deleteUtente"}, method = RequestMethod.POST)
	public String deleteUtente(HttpServletRequest request)
	{
		Utente utente = this.utenteService.getUtente(Long.parseLong(request.getParameter("utente")));

		String comando = request.getParameter("submit");

		if(comando.equals("elimina")) 
		{
			utenteService.delete(utente);
		}

		return "userList";
	}
	
	
}
