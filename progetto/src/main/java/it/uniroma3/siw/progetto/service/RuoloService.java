package it.uniroma3.siw.progetto.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.progetto.model.Ruolo;
import it.uniroma3.siw.progetto.repository.RuoloRepository;

public class RuoloService 
{
	@Autowired
	protected RuoloRepository ruoloRepository;
	
	@Transactional
	public Ruolo getRuolo(String username)
	{
		Optional<Ruolo> result = ruoloRepository.findByUsername(username);
		
		return result.orElse(null);
	}
}
