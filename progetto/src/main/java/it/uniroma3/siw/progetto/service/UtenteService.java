package it.uniroma3.siw.progetto.service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.progetto.model.Utente;
import it.uniroma3.siw.progetto.repository.UtenteRepository;

@Service
public class UtenteService 
{	
	@Autowired
	protected UtenteRepository utenteRepository;
	
	/* Trova un utente in base al suo id */
	@Transactional
	public Utente getUtente(long id)
	{
		Optional<Utente> result = this.utenteRepository.findById(id);
		
		return result.orElse(null);
	}
	
	/* Trova un utente in base al suo username */
	@Transactional
	public Utente findByUsername(String username)
	{
		if((username.isEmpty()) || username == null)
		{
			return null;
		}
		
		Optional<Utente> result = this.utenteRepository.findByUsername(username);
		
		return result.orElse(null);
	}
	
	/* Memorizza un utente nel db */
	@Transactional
	public void save(Utente r)
	{
		this.utenteRepository.save(r);
	}
}
