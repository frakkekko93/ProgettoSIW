package it.uniroma3.siw.progetto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.repository.TagRepository;

@Service
public class TagService 
{
	@Autowired
	protected TagRepository tagRepository;
}
