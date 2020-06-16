package it.uniroma3.siw.progetto.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.controller.validator.TaskValidator;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.TaskService;

@Controller
public class TaskController 
{
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected ProgettoService progettoService;
	
	/* Effettua un operazione sul progetto selezionato */
	@RequestMapping(value= {"/editTasks"}, method = RequestMethod.POST)
	public String editProject(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		String comando = request.getParameter("submit");
		String vista = "";
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);

		/* Rimanda alla pagina di visualizzazione del progetto */
		if(comando.equals("add"))
		{
			vista = "addTask";
		}
		
		/* Rimanda alla for di modifica del progetto */
		if(comando.equals("update"))
		{
			vista = "updateTask";
		}
		
		/* Elimina il progetto */
		if(comando.equals("delete"))
		{
			progetto.getTasks().remove(task);
			this.taskService.delete(task);
			vista = "project";
		}
		
		return vista;
	}
	
	/* Aggiorna i dati di un progetto */
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
				this.taskService.save(task);
				this.taskService.addTask(progetto, task);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("nomeText", nome);
				model.addAttribute("descrizioneText", descrizione);
				return "addTask";
			}
		}

		model.addAttribute("progetto", progetto);
        return "project";
	}
	
	/* Aggiorna i dati di un task */
	@RequestMapping(value= {"/updateTask"}, method = RequestMethod.POST)
	public String updateTask(@AuthenticationPrincipal OAuth2User principal, Model model, HttpServletRequest request)
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

			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Aggiorno il task e memorizzo i cambiamenti nel db */
				task.setNome(nome);
				task.setDescrizione(descrizione);
				this.taskService.save(task);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("nomeText", nome);
				model.addAttribute("descrizioneText", descrizione);
				return "addTask";
			}
		}

		model.addAttribute("progetto", progetto);
        return "project";
	}
}
