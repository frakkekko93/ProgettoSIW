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
import it.uniroma3.siw.progetto.controller.validator.TaskValidator;
import it.uniroma3.siw.progetto.model.Commento;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.service.CommentoService;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.TaskService;
import it.uniroma3.siw.progetto.service.UtenteService;

@Controller
public class TaskController 
{
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected ProgettoService progettoService;
	
	@Autowired
	protected UtenteService utenteService;
	
	@Autowired
	protected SessionData sessionData;
	
	@Autowired
	protected CommentoService commentoService;
	
	/* Mostra le informazioni del task */
	@RequestMapping(value= {"/showTask"})
	public String showTask(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
		
		return "task";
	}
	
	/* Mostra le informazioni del task di un progetto di cui sono membro */
	@RequestMapping(value= {"/showOnlyTask"}, method = RequestMethod.POST)
	public String showOnlyTask(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
		
		return "taskShowOnly";
	}
	
	/* Effettua un operazione sul progetto selezionato */
	@RequestMapping(value= {"/assignedTask/complete"}, method = RequestMethod.POST)
	public String completeAssignedTask(Model model, HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal)
	{
		Utente membro = sessionData.getLoggedUser(principal);
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));

		task.setCompletato(!task.isCompletato());
		this.taskService.save(task);
		
		model.addAttribute("assignedTasks", taskService.assignedTasks(progetto, membro));
		model.addAttribute("task", task);
		model.addAttribute("progetto", progetto);
		return "assignedTask";
	}
	
	/* Effettua un operazione sul progetto selezionato */
	@RequestMapping(value= {"/editTasks"}, method = RequestMethod.POST)
	public String editTasks(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = null;
		String comando = request.getParameter("submit");
		String vista = "";
		
		/* Rimanda all form di inserimento di un task */
		if(comando.equals("add"))
		{
			vista = "addTask";
		}
		else
		{
			/* Prendo il task */
			task=this.taskService.getTask(Long.parseLong(request.getParameter("task")));
			
			/* Rimanda all form di assegnazione di un task ad un membro */
			if(comando.equals("assign"))
			{
				vista = "assignTask";
			}
			
			/* Rimanda all form di assegnazione di un task ad un membro */
			if(comando.equals("complete"))
			{
				task.setCompletato(!task.isCompletato());
				this.taskService.save(task);
				vista = "task";
			}
			
			/* Rimanda all form di assegnazione di un tag ad un tag */
			if(comando.equals("assignTag"))
			{
				vista = "assignTag";
			}
			
			/* Rimanda alla for di modifica del task */
			if(comando.equals("update"))
			{
				vista = "updateTask";
			}
			
			/* Elimina il task */
			if(comando.equals("delete"))
			{
				progetto.getTasks().remove(task);
				this.taskService.delete(task);
				vista = "project";
			}
			
			/* Aggiungo il task al modello */
			model.addAttribute("task", task);
		}
		
		model.addAttribute("progetto", progetto);
		return vista;
	}
	
	/* Aggiunge un task al progetto */
	@RequestMapping(value= {"/addTask"}, method = RequestMethod.POST)
	public String addTask(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		String comando = request.getParameter("button");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		
		/* Se non e' stata annullata l'operazione */
		if(comando.equals("invia"))
		{
			TaskValidator validator = new TaskValidator();

			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Aggiungo il task al progetto */
				Task task = new Task();
				task.setNome(nome);
				task.setDescrizione(descrizione);
				
				this.taskService.addTask(progetto, task);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("nomeText", nome);
				model.addAttribute("descrizioneText", descrizione);
				model.addAttribute("progetto", progetto);
				return "addTask";
			}
		}

		model.addAttribute("progetto", progetto);
        return "project";
	}
	
	/* Aggiorna i dati di un task */
	@RequestMapping(value= {"/updateTask"}, method = RequestMethod.POST)
	public String updateTask(Model model, HttpServletRequest request)
	{
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		String comando = request.getParameter("button");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		
		/* Se non e' stata annullata l'operazione */
		if(comando.equals("invia"))
		{
			TaskValidator validator = new TaskValidator();

			task.setNome(nome);
			task.setDescrizione(descrizione);
		
			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Memorizzo i cambiamenti nel db */
				this.taskService.save(task);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("task", task);
				model.addAttribute("progetto", progetto);
				return "updateTask";
			}
		}

		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
        return "task";
	}
	
	/* Assegna un task ad un membro del progetto */
	@RequestMapping(value= {"/assignTask"}, method = RequestMethod.POST)
	public String assignTask(Model model, HttpServletRequest request)
	{
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Utente membro = this.utenteService.getUtente(Long.parseLong(request.getParameter("membro")));
		String comando = request.getParameter("submit");
		
		/* Se non e' stata annullata l'operazione */
		if(comando.equals("assign"))
		{
			/* Aggiorno il task e memorizzo i cambiamenti nel db */
			task.setResponsabile(membro);
			this.taskService.save(task);
		}

		model.addAttribute("task", task);
		model.addAttribute("progetto", progetto);
        return "task";
	}
	
	@RequestMapping(value= {"/showFormComment"}, method = RequestMethod.POST)
	public String showFormComment(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
		return "addComment";
	}
	
	
	/* Aggiungi un commento al task*/
	@RequestMapping(value= {"/addComment"}, method = RequestMethod.POST)
	public String addComment(Model model, HttpServletRequest request,@AuthenticationPrincipal OAuth2User principal)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		Utente utente = this.sessionData.getLoggedUser(principal);
		String user = utente.getUsername();
		String comando = request.getParameter("button");
		String testo = request.getParameter("testo");
		
		
		if(comando.equals("invia"))
		{
			if(testo == null || testo.isEmpty())
			{
				model.addAttribute("testoText", testo);
				model.addAttribute("progetto", progetto);
				model.addAttribute("task", task);
				model.addAttribute("erroreCommento", "Il commento è obbligatorio");
				return "addComment";
			}
			else
			{
				Commento commento = new Commento();
				commento.setTesto(testo);
				commento.setAutore(user);
				commento.setTask(task);
				commento = commentoService.save(commento);
				task.getCommenti().add(commento);
				this.taskService.save(task);				
			}
		}
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
		
		return "taskShowOnly";
	}
}
