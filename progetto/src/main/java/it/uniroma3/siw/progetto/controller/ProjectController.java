package it.uniroma3.siw.progetto.controller;

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
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.ProgettoService;

@Controller
public class ProjectController 
{
	@Autowired
	protected ProgettoService progettoService;
	
	@Autowired
	protected SessionData sessionData;
	
	/* Mostra la form per creare un nuovo progetto */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String showProjectForm() 
    {
        return "insertProject";
    }
	
	/* Registra i dati del nuovo progetto */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public String registerProject(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) 
    {
		Utente utenteLoggato = sessionData.getLoggedUser(principal);
		
		Progetto project = new Progetto();
        project.setNome((String)request.getParameter("nome"));
        project.setDescrizione((String)request.getParameter("descrizione"));
        project.setProprietario(utenteLoggato);
        project = progettoService.save(project);
        
        return "home";
    }
	
	/* Visualizza la lista dei progetti dell'utente loggato */
	@RequestMapping(value = { "/myProjects" }, method = RequestMethod.GET)
    public String showProjectList(@AuthenticationPrincipal OAuth2User principal, Model model) 
    {
		Utente utenteLoggato = sessionData.getLoggedUser(principal);
		
		List<Progetto> projects = this.progettoService.findByProprietario(utenteLoggato);
		
		model.addAttribute("projects", projects);
        
        return "projectList";
    }
}
