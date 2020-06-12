package it.uniroma3.siw.progetto.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Progetto;
import it.uniroma3.siw.progetto.repository.ProgettoRepository;

@Service
public class ProgettoService 
{
	@Autowired
	protected ProgettoRepository progettoRepository;
	
	@Transactional
	public Progetto save(Progetto progetto)
	{
		return this.progettoRepository.save(progetto);
	}
}
