package it.uniroma3.siw.progetto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progetto.model.Commento;
import it.uniroma3.siw.progetto.repository.CommentoRepository;


@Service
public class CommentoService 
{
	@Autowired
	protected CommentoRepository commentoRepository;
	
	public Commento save(Commento commento)
	{
		return this.commentoRepository.save(commento);
	}
}
