package it.uniroma3.siw.progetto.service;

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
	
	@Transactional
	public Tag save(Tag tag)
	{
		return tagRepository.save(tag);
	}
}
