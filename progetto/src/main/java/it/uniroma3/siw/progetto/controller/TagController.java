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
import it.uniroma3.siw.progetto.service.ProgettoService;
import it.uniroma3.siw.progetto.service.TagService;

@Controller
public class TagController {

	@Autowired
	protected TagService tagService;
	
	@Autowired
	protected ProgettoService progettoService;
	
	@RequestMapping(value= {"/addTag"}, method = RequestMethod.GET)
	public String showAddTag(Model model, HttpServletRequest request) 
	{
		Progetto progetto = this.progettoService.getProgetto(Long.parseLong(request.getParameter("progetto")));
		model.addAttribute("progetto", progetto);
		return "addTag";
	}
	
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
				/* Aggiungo il task al progetto */
				Tag tag = new Tag();
				tag.setNome(nome);
				tag.setDescrizione(descrizione);
				tag.setColore(colore);
				tag = tagService.save(tag);
				progetto.getTags().add(tag);
				progettoService.save(progetto);
				
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
}
