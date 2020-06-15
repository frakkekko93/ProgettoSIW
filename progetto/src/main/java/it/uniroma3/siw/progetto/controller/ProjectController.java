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
import it.uniroma3.siw.progetto.controller.validator.ProjectValidator;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class ProjectController
{
	@Autowired
	protected ProgettoService progettoService;

	@Autowired UtenteService utenteService;

	@Autowired
	protected SessionData sessionData;

	/* Mostra la form per creare un nuovo progetto */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String showProjectForm(HttpServletRequest request)
    {
        return "insertProject";
    }

	/* Registra i dati del nuovo progetto */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public String registerProject(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request, Model model)
    {
		Utente utenteLoggato = sessionData.getLoggedUser(principal);

		String comando = request.getParameter("button");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");

		/* Se non e' stata annullata l'operazione */
		if(comando.equals("invia"))
		{
			ProjectValidator validator = new ProjectValidator();

			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Creo il progetto e lo salvo nel db */
				Progetto project = new Progetto();
		        project.setNome((String)request.getParameter("nome"));
		        project.setDescrizione((String)request.getParameter("descrizione"));
		        project.setProprietario(utenteLoggato);
	        	project = progettoService.save(project);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("nomeText", nome);
				model.addAttribute("descrizioneText", descrizione);
				return "insertProject";
			}
		}

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

	/* Mostra la form di modifica del progetto */
	@RequestMapping(value= {"/editProject"}, method = RequestMethod.POST)
	public String showProjectUpdateForm(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));

		model.addAttribute("progetto", progetto);

		return "updateProject";
	}

	/* Aggiorna i dati di un progetto */
	@RequestMapping(value= {"/updateProject"}, method = RequestMethod.POST)
	public String updateProject(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String comando = request.getParameter("button");

		/* Se non e' stata annullata l'operazione */
		if(comando.equals("aggiorna"))
		{
			ProjectValidator validator = new ProjectValidator();

			progetto.setNome(nome);
			progetto.setDescrizione(descrizione);

			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Salvo le modifiche */
				progetto = progettoService.save(progetto);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form con gli errori */
				model.addAttribute("progetto", progetto);
				return "updateProject";
			}
		}

		return this.showProjectList(principal, model);
	}

	/* Cancella un progetto */
	@RequestMapping(value= {"/deleteProject"}, method = RequestMethod.POST)
	public String delete(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));

		this.progettoService.delete(progetto);

		return this.showProjectList(principal, model);
	}
	
	@RequestMapping(value= {"/shareMyProject"}, method = RequestMethod.POST)
	public String showProjectShareForm(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));

		model.addAttribute("progetto", progetto);

		return "shareProject";
	}
	
	
	
	/*Condivi il progetto con un altro utente*/
	@RequestMapping(value = { "/share" }, method = RequestMethod.POST)
	public String share(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal, Model model)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
				
		String membro = request.getParameter("membroInput");
		
		String comando = request.getParameter("submit");
		
		Utente utente = utenteService.getUtente(membro);
		
		if(comando.equals("condividi")) 
		{
			
			if(utente == null || membro.isEmpty())
			{
				request.setAttribute("nonEsiste", "L'utente digitato non esiste");
				model.addAttribute("progetto", progetto);
				return "shareProject";
			}
			else 
			{
				progettoService.condividiProgettoConUtente(progetto, utente);
			}
		}
		
		
		return this.showProjectList(principal, model);
	}
	
	/* Visualizza la lista dei progetti condivisi con me */
	@RequestMapping(value = { "/shareProjects" }, method = RequestMethod.GET)
    public String showProjectshareWithMeList(@AuthenticationPrincipal OAuth2User principal, Model model)
    {
		Utente utenteLoggato = sessionData.getLoggedUser(principal);

		List<Progetto> projects = this.progettoService.findByMembri(utenteLoggato);
		model.addAttribute("projects", projects);
        
		return "shareWithMeList";
    }
	
}
