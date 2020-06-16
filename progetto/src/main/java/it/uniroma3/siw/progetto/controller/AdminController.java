package it.uniroma3.siw.progetto.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.RuoloService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class AdminController {
	
	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected RuoloService ruoloService;
	
	@Autowired
	protected ProgettoService progettoService;

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
	public String deleteUtente(HttpServletRequest request, Model model)
	{
		Utente utente = this.utenteService.getUtente(Long.parseLong(request.getParameter("utente")));

		String comando = request.getParameter("submit");

		if(comando.equals("elimina")) 
		{
			List<Progetto> visibili = utente.getProgettiVisibili();
		    Iterator<Progetto> iterator = visibili.iterator();
		    while(iterator.hasNext()) 
		    {
		    	Progetto p = iterator.next();
		    	p.getMembri().clear();
		    	this.progettoService.save(p);
		    }
		    
		    Ruolo r = this.ruoloService.getRuolo(utente);
		    this.ruoloService.delete(r);
		    
			utenteService.delete(utente);
		}
		
		List<Utente> utenti = this.utenteService.getAllUsers();
		model.addAttribute("utenti", utenti);
		

		return "userList";
	}
	
	
}
