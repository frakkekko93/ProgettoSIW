package it.uniroma3.siw.progetto.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.controller.validator.TaskValidator;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.model.Utente;
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
	
	/* Effettua un operazione sul progetto selezionato */
	@RequestMapping(value= {"/editTasks"}, method = RequestMethod.POST)
	public String editProject(Model model, HttpServletRequest request)
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
			/* Aggiungo il task al modello */
			task=this.taskService.getTask(Long.parseLong(request.getParameter("task")));
			model.addAttribute("task", task);
			
			/* Rimanda all form di assegnazione di un task ad un membro*/
			if(comando.equals("assign"))
			{
				vista = "assignTask";
			}
			
			/* Rimanda alla for di modifica del task */
			if(comando.equals("update"))
			{
				task=this.taskService.getTask(Long.parseLong(request.getParameter("task")));
				model.addAttribute("task", task);
				vista = "updateTask";
			}
			
			/* Elimina il task */
			if(comando.equals("delete"))
			{
				task=this.taskService.getTask(Long.parseLong(request.getParameter("task")));
				progetto.getTasks().remove(task);
				this.taskService.delete(task);
				vista = "project";
			}
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
				progetto.getTasks().add(task);
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
				return "updateTask";
			}
		}

		model.addAttribute("progetto", progetto);
        return "project";
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

		model.addAttribute("progetto", progetto);
        return "project";
	}
}