package it.uniroma3.siw.progetto.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.model.Task;
import it.uniroma3.siw.progetto.repository.TagRepository;

@Service
public class TagService 
{
	@Autowired
	protected TagRepository tagRepository;
	
	@Autowired
	protected ProgettoService progettoService;
	
	@Autowired
	protected TaskService taskService;
	
	/* Salva un tag nel db */
	@Transactional
	public Tag save(Tag tag)
	{
		return tagRepository.save(tag);
	}
	
	/* Prende un tag dal db */
	public Tag getTag(Long id)
	{
		Optional<Tag> result = this.tagRepository.findById(id); 
		
		return result.orElse(null);
	}
	
	/* Aggiunge un tag ad un progetto */
	public void addTag(Progetto progetto, Tag tag)
	{
		if(!progetto.getTags().contains(tag))
		{
			progetto.getTags().add(tag);
			this.progettoService.save(progetto);
		}
	}
	
	/* Aggiunge un tag ad un progetto */
	public void addTagToTask(Task task, Tag tag)
	{
		if(!task.getTags().contains(tag))
		{
			task.getTags().add(tag);
			this.taskService.save(task);
		}
	}
}
