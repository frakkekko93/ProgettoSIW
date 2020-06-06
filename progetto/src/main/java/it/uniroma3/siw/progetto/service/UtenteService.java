package it.uniroma3.siw.progetto.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.UtenteRepository;

@Service
public class UtenteService 
{	
	protected UtenteRepository utenteRepository;
	
	@Transactional
	public Utente getUtente(Long id)
	{
		Optional<Utente> result = utenteRepository.findById(id);
		
		return result.orElse(null);
	}
}
