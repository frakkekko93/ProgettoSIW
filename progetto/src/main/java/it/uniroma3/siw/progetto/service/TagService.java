package it.uniroma3.siw.progetto.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Tag;
import it.uniroma3.siw.progetto.repository.TagRepository;

@Service
public class TagService 
{
	@Autowired
	protected TagRepository tagRepository;
	
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
}
