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
import it.uniroma3.siw.progetto.service.TaskService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class ProjectController
{
	@Autowired
	protected ProgettoService progettoService;

	@Autowired 
	protected UtenteService utenteService;

	@Autowired
	protected SessionData sessionData;
	
	@Autowired
	protected TaskService taskService;
	

	/* Mostra la form per creare un nuovo progetto */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String showCreateProjectForm()
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

	/* Mostra la pagina del progetto */
	@RequestMapping(value = { "/project" })
    public String showProjectPage(Model model, HttpServletRequest request)
    {
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		model.addAttribute("progetto", progetto);
        return "project";
    }
	
	/* Effettua un operazione sul progetto selezionato */
	@RequestMapping(value= {"/editProject", "/show"}, method = RequestMethod.POST)
	public String editProject(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		String comando = request.getParameter("submit");
		String vista = "";
		
		model.addAttribute("progetto", progetto);

		/* Rimanda alla pagina di visualizzazione del progetto */
		if(comando.equals("show"))
		{
			vista = "project";
		}
		
		/* Rimanda alla for di modifica del progetto */
		if(comando.equals("update"))
		{
			vista = "updateProject";
		}
		
		/* Rimanda alla form di condivisione del progetto */
		if(comando.equals("share"))
		{
			vista = "shareProject";
		}
		
		/* Elimina il progetto */
		if(comando.equals("delete"))
		{
			this.progettoService.delete(progetto);
		
			vista = this.showProjectList(principal, model);
		}
		
		/*Aggiungi tag al progetto*/
		if(comando.equals("addTag"))
		{
			vista = "addTag";
		}
		
		return vista;
	}

	/* Aggiorna i dati di un progetto */
	@RequestMapping(value= {"/updateProject"}, method = RequestMethod.POST)
	public String updateProject(Model model, HttpServletRequest request)
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

		model.addAttribute("progetto", progetto);
		return "project";
	}
	
	/*Condivi il progetto con un altro utente*/
	@RequestMapping(value = { "/share" }, method = RequestMethod.POST)
	public String share(HttpServletRequest request, Model model)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));		
		String membro = request.getParameter("membroInput");
		String comando = request.getParameter("submit");
		Utente utente = utenteService.getUtente(membro);
		
		if(comando.equals("condividi")) 
		{
			/* Utente insesistente */
			if(utente == null || membro.isEmpty())
			{
				request.setAttribute("nonEsiste", "L'utente digitato non esiste");
				model.addAttribute("progetto", progetto);
				return "shareProject";
			}
			else //Utente esistente
			{
				this.progettoService.condividiProgettoConUtente(progetto, utente);
			}
		}
		
		model.addAttribute("progetto", progetto);
		
		return "project";
	}
	
	/* Visualizza la lista dei progetti condivisi con me */
	@RequestMapping(value = { "/shareProjects" }, method = RequestMethod.GET)
    public String showProjectSharedWithMe(@AuthenticationPrincipal OAuth2User principal, Model model)
    {
		Utente utenteLoggato = sessionData.getLoggedUser(principal);

		List<Progetto> projects = this.progettoService.findByMembri(utenteLoggato);
		model.addAttribute("projects", projects);
		        
		return "shareWithMeList";
    }
	
	/* Permette di editare i membri di un progetto */
	@RequestMapping(value = {"/editMembers"}, method = RequestMethod.POST)
	public String editMembers(HttpServletRequest request, Model model)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Utente u = this.utenteService.getUtente(Long.parseLong(request.getParameter("membro")));
		String comando = request.getParameter("submit");
		String vista = "";		
		
		if(comando.equals("deleteMembro"))
		{
			progetto = this.progettoService.deleteMembro(progetto, u);
			model.addAttribute("progetto", progetto);
			
			vista = "project";
		}
		
		model.addAttribute("progetto", progetto);

		return vista;
	}
	
	/* Mostra la pagina descrittiva del progetto ad un membro */
	@RequestMapping(value= {"/showOnlyProject"})
	public String showOnlyProject(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
	{
		Utente membro = sessionData.getLoggedUser(principal);
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("assignedTasks", taskService.assignedTasks(progetto, membro));

		return "assignedTask";
	}
}