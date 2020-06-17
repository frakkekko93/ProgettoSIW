package it.uniroma3.siw.progetto.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.progetto.controller.validator.TagValidator;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.TagService;
import it.uniroma3.siw.progetto.service.TaskService;

@Controller
public class TagController 
{
	@Autowired
	protected TagService tagService;
	
	@Autowired
	protected ProgettoService progettoService;
	
	@Autowired
	protected TaskService taskService;
	
	/* Mostra la form di aggiunta del tag */
	@RequestMapping(value= {"/addTag"}, method = RequestMethod.GET)
	public String showAddTag(Model model, HttpServletRequest request) 
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		model.addAttribute("progetto", progetto);
		return "addTag";
	}
	
	/* Aggiunge un tag al progetto */
	@RequestMapping(value= {"/addTag"}, method = RequestMethod.POST)
	public String addTag(Model model, HttpServletRequest request)
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		String comando = request.getParameter("button");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String colore = request.getParameter("colore");
		
		/* Se non e' stata annullata l'operazione */
		if(comando.equals("invia"))
		{
			TagValidator validator = new TagValidator();

			/* Se i dati sono validi */
			if(validator.validate(request))
			{
				/* Aggiungo il tag al progetto */
				Tag tag = new Tag();
				tag.setNome(nome);
				tag.setDescrizione(descrizione);
				tag.setColore(colore);
				tag = tagService.save(tag);
				this.tagService.addTag(progetto, tag);
			}
			else
			{
				/* Salvo i campi inseriti dall'utente e torno alla form */
				model.addAttribute("nomeText", nome);
				model.addAttribute("descrizioneText", descrizione);
				model.addAttribute("coloreText", colore);
				model.addAttribute("progetto", progetto);
				return "addTag";
			}
		}

		model.addAttribute("progetto", progetto);
        return "project";
	}
	
	/* Assegna un tag ad un task del progetto */
	@RequestMapping(value={"/assignTag"}, method=RequestMethod.POST)
	public String assignTag(Model model, HttpServletRequest request)
	{
		Tag tag = this.tagService.getTag(Long.parseLong(request.getParameter("tag")));
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		String comando = request.getParameter("submit");
		
		if(comando.equals("assign"))
		{
			/* Aggiungo il tag al task */
			this.tagService.addTagToTask(task, tag);
		}
		
		model.addAttribute("task", task);
		model.addAttribute("progetto", progetto);
		return "task";
	}
	
	/* Elimina un tag del progetto */
	@RequestMapping(value={"/deleteTag"}, method=RequestMethod.POST)
	public String deleteTag(Model model, HttpServletRequest request) 
	{
		Tag tag = this.tagService.getTag(Long.parseLong(request.getParameter("tag")));
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		
		this.progettoService.deleteTag(tag, progetto);
		
		model.addAttribute("progetto", progetto);
		return "project";
	}
	
	/* Elimina un tag da un task */
	@RequestMapping(value={"/deleteTagOnTask"}, method=RequestMethod.POST)
	public String deleteTagOnTask(Model model, HttpServletRequest request) 
	{
		Tag tag = this.tagService.getTag(Long.parseLong(request.getParameter("tag")));
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		Task task = this.taskService.getTask(Long.parseLong(request.getParameter("task")));
		
		this.taskService.deleteTag(tag, task);
		
		model.addAttribute("progetto", progetto);
		model.addAttribute("task", task);
		return "task";
	}
}
